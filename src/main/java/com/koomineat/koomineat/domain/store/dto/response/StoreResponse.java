package com.koomineat.koomineat.domain.store.dto.response;

import com.koomineat.koomineat.domain.store.entity.Store;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "스토어 정보 응답 DTO")
public class StoreResponse {

    private Long storeId;
    private String name;

    @Schema(description = "위치 ID", example = "3")
    private Long locationId;

    @Schema(description = "위치 이름", example = "예술관")
    private String locationName;

    private Long categoryId;
    private String categoryName;

    public static StoreResponse from(Store s) {
        return StoreResponse.builder()
                .storeId(s.getId())
                .name(s.getName())
                .locationId(s.getLocation().getId())
                .locationName(s.getLocation().getName())
                .categoryId(s.getCategory().getId())
                .categoryName(s.getCategory().getName())
                .build();
    }
}