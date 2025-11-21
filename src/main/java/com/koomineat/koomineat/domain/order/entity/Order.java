package com.koomineat.koomineat.domain.order.entity;

import com.koomineat.koomineat.domain.auth.entity.User;
import com.koomineat.koomineat.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 주문 상태 (Enum)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private int totalPrice;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "ended_at", nullable = true)
    private LocalDateTime endedAt;

    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    public void addPrice(int price)
    {
        this.totalPrice += price;
    }

    public void addOrderItem(OrderItem item) {
        this.orderItems.add(item);
        item.setOrder(this);
        this.addPrice(item.getPrice() * item.getQuantity());
    }

    // order type 바꿀 시 사용.
    public void changeOrderType(OrderType orderType) {
        this.orderType = orderType;

        if (orderType == OrderType.PICKUP) {
            this.status = OrderStatus.FINISHED;
            this.endedAt = LocalDateTime.now();
        }
    }
}
