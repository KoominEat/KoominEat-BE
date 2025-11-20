package com.koomineat.koomineat.domain.delivery.dto.response;

import com.koomineat.koomineat.domain.delivery.entity.Delivery;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeliveryResponse {

    private Long deliveryId;
    private Long orderId;
    private String status;
    private String destination;
    private String message;
    private Long deliveryUserId;

    public static DeliveryResponse from(Delivery d) {
        return DeliveryResponse.builder()
                .deliveryId(d.getId())
                .orderId(d.getOrder().getId())
                .status(d.getStatus().name())
                .destination(d.getDestination())
                .message(d.getMessage())
                .deliveryUserId(
                        d.getDeliveryUser() == null ? null : d.getDeliveryUser().getId()
                )
                .build();
    }
}