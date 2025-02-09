package ru.sw.stock_price_monitoring.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "stock")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Stock extends BaseEntity{

    @Column(name = "ticker", nullable = false, unique = true, length = 5)
    private String ticker;
}

