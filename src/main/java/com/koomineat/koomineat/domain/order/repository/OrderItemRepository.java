package com.koomineat.koomineat.domain.order.repository;

import com.koomineat.koomineat.domain.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
