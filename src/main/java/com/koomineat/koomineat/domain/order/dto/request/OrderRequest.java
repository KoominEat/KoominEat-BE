package com.koomineat.koomineat.domain.order.dto.request;

import com.koomineat.koomineat.domain.order.entity.OrderType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {

    @NotNull
    private Long storeId;

    // 메뉴는 최소 1개 이상
    @NotNull
    @Size(min = 1)
    private List<@Valid OrderItemRequest> menus;

    @NotNull
    private OrderType orderType;   // DELIVERY / PICKUP

    // DELIVERY일 때만 사용, PICKUP이면 null 가능
    private String destination;    // 전달 위치
    private String message;        // 한줄 메시지
}