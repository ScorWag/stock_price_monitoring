package ru.sw.stock_price_monitoring.entity;


import jakarta.persistence.*;
import lombok.*;
import ru.sw.stock_price_monitoring.enums.TokenType;

@Entity
@Table(name = "token")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Token extends BaseEntity {

    @Column
    private String token;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "token_type")
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
}