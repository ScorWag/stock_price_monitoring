package ru.sw.stock_price_monitoring.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.sw.stock_price_monitoring.enums.RoleType;

import java.util.List;

@Entity
@Table(name = "role")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "role_type")
    private RoleType roleType;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permission",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_id")}
    )
    private List<Permission> permissions;
}
