package com.koomineat.koomineat.domain.order.dto.request;

import com.koomineat.koomineat.domain.order.entity.OrderType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderRequest {
    private Long storeId;
    private List<OrderItemRequest> menus;
    private OrderType orderType;
}
