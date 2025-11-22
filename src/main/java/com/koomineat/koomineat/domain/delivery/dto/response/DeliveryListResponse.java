package com.koomineat.koomineat.domain.delivery.dto.response;

import com.koomineat.koomineat.domain.auth.dto.response.UserResponse;
import com.koomineat.koomineat.domain.order.dto.response.OrderResponse;
import com.koomineat.koomineat.domain.delivery.entity.Delivery;
import com.koomineat.koomineat.domain.store.entity.Location;
import com.koomineat.koomineat.domain.store.entity.Store;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "전달 요청/전달 현황 응답 DTO")
public class DeliveryListResponse {

    @Schema(description = "전달 ID", example = "12")
    private Long deliveryId;

    @Schema(description = "전달 상태 (READY / DELIVERING / FINISHED / CANCELED)", example = "READY")
    private String status;

    @Schema(description = "주문 상세 정보")
    private OrderResponse order;

    @Schema(description = "전달을 수락한 사용자 정보 (미수락 시 null)")
    private UserResponse deliveryUser;

    @Schema(description = "전달 목적지", example = "예술관 302호")
    private String destination;

    @Schema(description = "요청 메시지", example = "빨리 부탁드립니다!")
    private String message;

    @Schema(description = "예상 전달 시간(분). 현재 null이며 추후 계산하여 반영.", example = "3")
    private Integer estimatedTime;

    @Schema(description = "픽업 매장 위치 ID", example = "3")
    private Long storeLocationId;

    @Schema(description = "픽업 매장 위치 이름", example = "예술관")
    private String storeLocationName;

    public static DeliveryListResponse from(Delivery d) {

        Store store = d.getOrder() != null ? d.getOrder().getStore() : null;
        Location location = (store != null) ? store.getLocation() : null;

        Long locationId = (location != null) ? location.getId() : null;
        String locationName = (location != null) ? location.getName() : null;

        return DeliveryListResponse.builder()
                .deliveryId(d.getId())
                .status(d.getStatus().name())
                .order(OrderResponse.from(d.getOrder()))
                .deliveryUser(
                        d.getDeliveryUser() == null ? null : UserResponse.from(d.getDeliveryUser())
                )
                .destination(d.getDestination())
                .message(d.getMessage())
                .estimatedTime(d.getEstimatedTime())

                // 필터링에 사용될 매장 위치 정보
                .storeLocationId(locationId)
                .storeLocationName(locationName)

                .build();
    }
}