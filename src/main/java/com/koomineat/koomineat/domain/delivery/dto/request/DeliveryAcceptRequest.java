package com.koomineat.koomineat.domain.delivery.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class DeliveryAcceptRequest {

    @Schema(description = "전달 수락 시 전달자가 남길 메시지 (선택)")
    private String message;
}