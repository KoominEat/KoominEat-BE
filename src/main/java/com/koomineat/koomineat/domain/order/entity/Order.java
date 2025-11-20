package com.koomineat.koomineat.domain.order.entity;

import com.koomineat.koomineat.domain.auth.entity.User;
import com.koomineat.koomineat.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

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

    private Long totalPrice;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime endedAt;

    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

}
