package ru.sw.stock_price_monitoring.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import ru.sw.stock_price_monitoring.dao.UserRepository;
import ru.sw.stock_price_monitoring.entity.User;

import java.util.Optional;

public interface TestUserRepository extends UserRepository {

    @Override
    void deleteById(@NotNull Long aLong);


    @Override
    @NotNull
    @EntityGraph(attributePaths = {"stockDataList", "roles"})
    Optional<User> findById(@NotNull Long aLong);
}
