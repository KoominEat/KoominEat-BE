package com.koomineat.koomineat.domain.delivery.service;

import com.koomineat.koomineat.domain.delivery.dto.response.DeliveryListResponse;
import com.koomineat.koomineat.domain.delivery.entity.Delivery;
import com.koomineat.koomineat.domain.order.entity.Order;

import java.util.List;

public interface DeliveryService {

    Delivery createDelivery(Order order, String destination, String message);

    Delivery acceptDelivery(String authToken, Long deliveryId);

    Delivery finishDelivery(Long deliveryId);

    List<DeliveryListResponse> getRequestList(Long locationId);

    List<DeliveryListResponse> getMyAcceptedList(String authToken);

    List<DeliveryListResponse> getMyDeliveryRequests(String authToken);
}