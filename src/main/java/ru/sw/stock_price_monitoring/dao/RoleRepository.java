package ru.sw.stock_price_monitoring.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sw.stock_price_monitoring.entity.Role;
import ru.sw.stock_price_monitoring.enums.RoleType;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleType(RoleType roleType);
}
