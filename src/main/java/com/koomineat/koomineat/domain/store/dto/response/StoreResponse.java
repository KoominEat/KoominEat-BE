package com.koomineat.koomineat.domain.store.dto.response;

import com.koomineat.koomineat.domain.store.entity.Store;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "스토어 정보 응답 DTO")
public class StoreResponse {

    @Schema(description = "스토어 ID", example = "1")
    private Long storeId;

    @Schema(description = "스토어 이름", example = "예술관 카페")
    private String name;

    @Schema(description = "스토어 위치(건물 이름)", example = "예술관")
    private String location;

    public static StoreResponse from(Store store) {
        return StoreResponse.builder()
                .storeId(store.getId())
                .name(store.getName())
                .location(store.getLocation())
                .build();
    }
}