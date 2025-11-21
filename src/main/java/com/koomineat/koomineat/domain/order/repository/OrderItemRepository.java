package com.koomineat.koomineat.domain.order.repository;

import com.koomineat.koomineat.domain.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
