package com.koomineat.koomineat.domain.order.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class OrderItemRequest {
    private Long menuItemId;
    private int quantity;
}
