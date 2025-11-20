package com.koomineat.koomineat.domain.auth.entity;

import com.koomineat.koomineat.domain.order.entity.Order;
import com.koomineat.koomineat.domain.order.entity.OrderItem;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
// postgres와 충돌 피하기 위해.
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // UUID.
    @Column(unique = true)
    private String authToken;

    // orders
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        this.orders.add(order);
        order.setUser(this);
    }
}
