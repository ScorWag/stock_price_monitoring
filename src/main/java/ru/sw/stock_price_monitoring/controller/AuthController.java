package ru.sw.stock_price_monitoring.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sw.stock_price_monitoring.controller.doc.AuthControllerDocumentation;
import ru.sw.stock_price_monitoring.dto.JwtRegisterResponse;
import ru.sw.stock_price_monitoring.dto.SignIn;
import ru.sw.stock_price_monitoring.dto.SignUp;
import ru.sw.stock_price_monitoring.dto.JwtAuthenticationResponse;
import ru.sw.stock_price_monitoring.service.AuthenticationService;


@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class AuthController implements AuthControllerDocumentation {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<JwtRegisterResponse> signUp(@RequestBody @Valid SignUp request) {
        return new ResponseEntity<>(authenticationService.signUp(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignIn request) {
        return authenticationService.signIn(request);
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<JwtAuthenticationResponse> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) {

        return authenticationService.refreshToken(request, response);
    }
}
