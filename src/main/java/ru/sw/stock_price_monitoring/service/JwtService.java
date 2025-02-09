package ru.sw.stock_price_monitoring.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.sw.stock_price_monitoring.config.properties.SecretProperties;
import ru.sw.stock_price_monitoring.config.properties.SecurityProperties;
import ru.sw.stock_price_monitoring.dao.TokenRepository;
import ru.sw.stock_price_monitoring.entity.User;
import ru.sw.stock_price_monitoring.entity.Token;

import javax.crypto.SecretKey;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class JwtService {
    private final SecretProperties secretProperties;
    private final SecurityProperties securityProperties;
    private final TokenRepository tokenRepository;

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String email = extractEmail(token);
        boolean isActiveToken = tokenRepository.findByToken(token)
                .map(Token::getIsActive).orElse(false);

        return email.equals(userDetails.getUsername())
                && !isTokenExpired(token)
                && isActiveToken;
    }

    public String generateAccessToken(User user) {
        return generateToken(user, securityProperties.getAccessTokenExpiration());
    }

    public String generateRefreshToken(User user) {
        return generateToken(user, securityProperties.getRefreshTokenExpiration());
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private String generateToken(User user, long expireTime) {
        return Jwts.builder()
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSigningKey())
                .compact();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(secretProperties.getJwtSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
