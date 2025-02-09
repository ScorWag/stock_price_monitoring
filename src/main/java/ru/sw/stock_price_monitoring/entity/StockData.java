package ru.sw.stock_price_monitoring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stock_data")
@NamedEntityGraph(
        name = "stock-data-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("stock")
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class StockData extends BaseEntity{

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    private LocalDate date;

    private BigDecimal open;

    private BigDecimal close;

    private BigDecimal high;

    private BigDecimal low;

    @OneToMany(
            mappedBy = "stockData",
            cascade = CascadeType.PERSIST,
            orphanRemoval = true
    )
    @JsonIgnore
    @ToString.Exclude
    private List<UserStockDataRelation> users = new ArrayList<>();
}

