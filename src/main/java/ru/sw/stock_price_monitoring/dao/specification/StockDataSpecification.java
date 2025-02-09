package ru.sw.stock_price_monitoring.dao.specification;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import ru.sw.stock_price_monitoring.entity.*;

import java.time.LocalDate;

public final class StockDataSpecification {

    public static Specification<StockData> byUser(User user) {
        return (root, query, criteriaBuilder) -> {

            Join<UserStockDataRelation, StockData> relations = root.join(StockData_.USERS, JoinType.LEFT);

            return criteriaBuilder.equal(relations.get(UserStockDataRelation_.USER), user);
        };
    }

    public static Specification<StockData> byStock(Stock stock) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(StockData_.STOCK), stock);
    }

    public static Specification<StockData> greaterThanOrEqualTo(LocalDate date) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get(StockData_.DATE), date);
    }

    public static Specification<StockData> lessThanOrEqualTo(LocalDate date) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get(StockData_.DATE), date);
    }
}
