package com.koomineat.koomineat.domain.order.dto.response;

import com.koomineat.koomineat.domain.order.entity.Order;
import com.koomineat.koomineat.domain.order.entity.OrderItem;
import com.koomineat.koomineat.domain.order.entity.OrderStatus;
import com.koomineat.koomineat.domain.order.entity.OrderType;
import com.koomineat.koomineat.domain.store.dto.response.MenuItemResponse;
import com.koomineat.koomineat.domain.store.dto.response.StoreResponse;
import com.koomineat.koomineat.domain.store.entity.Store;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.List;

@Getter
@Builder

// response 형태 변경 필요.
// menu list 전부 보낼지 결정.
public class OrderResponse {
    private Long orderId;

    private OrderStatus status;

    private int totalPrice;

    private List<OrderItemResponse> orderItemResponses;
    private OrderType orderType;

    private StoreResponse storeResponse;

    public static OrderResponse from(Order o) {
        List<OrderItemResponse> orderItemResponses =  o.getOrderItems()
                .stream()
                .map(OrderItemResponse::from)
                .toList();

        return OrderResponse.builder()
                .orderId(o.getId())
                .status(o.getStatus())
                .totalPrice(o.getTotalPrice())
                .orderItemResponses(orderItemResponses)
                .orderType(o.getOrderType())
                .storeResponse(StoreResponse.from(o.getStore()))
                .build();
    }

}
