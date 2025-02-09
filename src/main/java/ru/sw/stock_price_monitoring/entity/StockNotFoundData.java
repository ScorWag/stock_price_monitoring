package ru.sw.stock_price_monitoring.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "stock_not_found_data")
@NamedEntityGraph(
        name = "stock-not-found-data-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("stock")
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class StockNotFoundData extends BaseEntity{

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    private LocalDate date;
}

