package ru.sw.stock_price_monitoring.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sw.stock_price_monitoring.entity.Token;
import ru.sw.stock_price_monitoring.enums.TokenType;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(String token);
    List<Token> findByUserIdAndIsActive(Long userId, boolean isActive);
    List<Token> findByUserIdAndIsActiveAndTokenType(Long userId, boolean isActive, TokenType tokenType);
}
