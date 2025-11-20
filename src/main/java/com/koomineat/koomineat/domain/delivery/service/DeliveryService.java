package com.koomineat.koomineat.domain.delivery.service;

import com.koomineat.koomineat.domain.delivery.entity.Delivery;
import com.koomineat.koomineat.domain.order.entity.Order;

public interface DeliveryService {

    // 주문 생성 시 호출 -> 배송 정보 초기화
    Delivery createDelivery(Order order, String destination, String message);

    // 전달자가 수락
    Delivery acceptDelivery(String authToken, Long deliveryId);

    // 전달 완료
    Delivery finishDelivery(Long deliveryId);
}