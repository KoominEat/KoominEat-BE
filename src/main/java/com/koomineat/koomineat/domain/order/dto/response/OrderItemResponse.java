package com.koomineat.koomineat.domain.order.dto.response;
import com.koomineat.koomineat.domain.order.entity.OrderItem;
import com.koomineat.koomineat.domain.store.dto.response.MenuItemResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder

// response 형태 변경 필요.
// menu list 전부 보낼지 결정.
public class OrderItemResponse {

    private MenuItemResponse menuItemResponse;
    private int quantity;

    public static OrderItemResponse from(OrderItem o) {
        return OrderItemResponse.builder()
                .menuItemResponse(MenuItemResponse.from(o.getMenuItem()))
                .quantity(o.getQuantity())
                .build();
    }
}
