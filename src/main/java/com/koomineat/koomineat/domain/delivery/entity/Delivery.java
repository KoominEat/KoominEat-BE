package com.koomineat.koomineat.domain.delivery.entity;

import com.koomineat.koomineat.domain.auth.entity.User;
import com.koomineat.koomineat.domain.order.entity.Order;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Delivery {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_user_id")
    private User deliveryUser;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatus status;

    @Column(nullable = false)
    private String destination;

    private String message;

    public void updateStatus(DeliveryStatus status) {
        this.status = status;
    }

    public void updateDeliveryUser(User user) {
        this.deliveryUser = user;
    }
}