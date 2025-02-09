package ru.sw.stock_price_monitoring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Entity
@Table(name = "user")
@Builder
public class User extends BaseEntity implements UserDetails {

    private String name;

    private String email;

    private String password;

    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private List<Role> roles;

    @ToString.Exclude
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<UserStockDataRelation> stockDataList = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .flatMap(role -> role.getPermissions().stream())
                .toList();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void addStockData(StockData stockData) {
        UserStockDataRelation userStockData = new UserStockDataRelation(this, stockData);
        stockDataList.add(userStockData);
        stockData.getUsers().add(userStockData);
    }

    public void removeStockData(StockData stockData) {
        for (Iterator<UserStockDataRelation> iterator = stockDataList.iterator();
             iterator.hasNext(); ) {
            UserStockDataRelation userStockDataRelation = iterator.next();

            if (userStockDataRelation.getUser().equals(this) &&
                    userStockDataRelation.getStockData().equals(stockData)) {
                iterator.remove();
                userStockDataRelation.getStockData().getUsers().remove(userStockDataRelation);
                userStockDataRelation.setUser(null);
                userStockDataRelation.setStockData(null);
            }
        }
    }
}
