package ru.sw.stock_price_monitoring.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sw.stock_price_monitoring.dao.RoleRepository;
import ru.sw.stock_price_monitoring.dao.TokenRepository;
import ru.sw.stock_price_monitoring.dto.JwtRegisterResponse;
import ru.sw.stock_price_monitoring.dto.SignUp;
import ru.sw.stock_price_monitoring.dto.SignIn;
import ru.sw.stock_price_monitoring.dto.JwtAuthenticationResponse;
import ru.sw.stock_price_monitoring.dto.mapper.UserMapper;
import ru.sw.stock_price_monitoring.entity.Token;
import ru.sw.stock_price_monitoring.entity.User;
import ru.sw.stock_price_monitoring.enums.RoleType;
import ru.sw.stock_price_monitoring.enums.TokenType;
import ru.sw.stock_price_monitoring.exception.UserAlreadyExistsException;

import java.util.List;

import static ru.sw.stock_price_monitoring.enums.TokenType.*;
import static ru.sw.stock_price_monitoring.constants.AuthenticationConstants.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    public JwtRegisterResponse signUp(SignUp request) {
        if (userService.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Пользователь с таким email уже зарегистрирован");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(List.of(roleRepository.findByRoleType(RoleType.USER)))
                .build();

        User createdUser = userService.create(user);

        String token = jwtService.generateAccessToken(user);
        saveUserTokenByType(jwtService.generateRefreshToken(user), user, REFRESH);
        saveUserTokenByType(token, user, ACCESS);

        return new JwtRegisterResponse(token, userMapper.toUserDto(createdUser));
    }

    public JwtAuthenticationResponse signIn(SignIn request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        User user = userService.findByEmail(request.getEmail());

        String accessToken = jwtService.generateAccessToken(user);
        List<Token> refreshTokens = tokenRepository.findByUserIdAndIsActiveAndTokenType(user.getId(), true, REFRESH);
        String refreshToken;
        if (refreshTokens.isEmpty()) refreshToken = jwtService.generateRefreshToken(user);
        else refreshToken = refreshTokens.get(0).getToken();

        revokeAllTokenByType(user, ACCESS);
        saveUserTokenByType(accessToken, user, ACCESS);

        return new JwtAuthenticationResponse(accessToken, refreshToken);
    }

    public ResponseEntity<JwtAuthenticationResponse> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) {

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER_PREFIX)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String refreshToken = authorizationHeader.substring(7);
        String email = jwtService.extractEmail(refreshToken);

        User user = userService.findByEmail(email);

        if (jwtService.isTokenValid(refreshToken, user)) {

            String accessToken = jwtService.generateAccessToken(user);

            revokeAllToken(user);

            saveUserTokenByType(accessToken, user, ACCESS);
            saveUserTokenByType(jwtService.generateRefreshToken(user), user, REFRESH);

            return new ResponseEntity<>(new JwtAuthenticationResponse(accessToken, refreshToken), HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    private void revokeAllTokenByType(User user, TokenType tokenType) {
        List<Token> tokens = tokenRepository.findByUserIdAndIsActiveAndTokenType(
                user.getId(),
                true,
                tokenType
        );

        revoke(tokens);

        tokenRepository.saveAll(tokens);
    }

    private void revokeAllToken(User user) {
        List<Token> tokens = tokenRepository.findByUserIdAndIsActive(user.getId(), true);
        revoke(tokens);
        tokenRepository.saveAll(tokens);
    }

    private void saveUserTokenByType(String tokenValue, User user, TokenType tokenType) {
        Token token = new Token();

        token.setToken(tokenValue);
        token.setIsActive(true);
        token.setTokenType(tokenType);
        token.setUser(user);

        tokenRepository.save(token);
    }

    private void revoke(List<Token> tokens) {
        if(!tokens.isEmpty()){
            tokens.forEach(t -> t.setIsActive(false));
        }
    }
}
