package ru.sw.stock_price_monitoring.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "permission")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Permission extends BaseEntity implements GrantedAuthority {
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }

    public static class PermissionConstant {
        public static final String GET_USER_STOCK_INFO_PERMISSION = "get_user_stock_info_permission";
        public static final String SAVE_STOCK_INFO_REQUEST_PERMISSION = "save_stock_info_request_permission";
    }
}
