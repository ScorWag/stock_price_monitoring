package ru.sw.stock_price_monitoring.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import ru.sw.stock_price_monitoring.dao.TokenRepository;
import ru.sw.stock_price_monitoring.entity.Token;

import static ru.sw.stock_price_monitoring.constants.AuthenticationConstants.*;

@RequiredArgsConstructor
@Component
public class CustomLogoutHandler implements LogoutHandler {
    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {

        String authHeader = request.getHeader(HEADER_AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            return;
        }

        String token = authHeader.substring(7);

        Token accessToken = tokenRepository.findByToken(token).orElse(null);

        if (accessToken != null) {
            accessToken.setIsActive(false);
            tokenRepository.save(accessToken);
        }
    }
}
