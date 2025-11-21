package com.koomineat.koomineat.domain.delivery.repository;

import com.koomineat.koomineat.domain.delivery.entity.Delivery;
import com.koomineat.koomineat.domain.delivery.entity.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    List<Delivery> findByStatusOrderByOrderCreatedAtAsc(DeliveryStatus status);

    List<Delivery> findByDeliveryUserIdOrderByOrderCreatedAtAsc(Long userId);

    List<Delivery> findByOrderUserIdOrderByOrderCreatedAtAsc(Long userId);

    List<Delivery> findByStatusAndOrderStoreLocationIdOrderByOrderCreatedAtAsc(
            DeliveryStatus status, Long locationId
    );
}