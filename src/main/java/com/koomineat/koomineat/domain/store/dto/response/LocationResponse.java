package com.koomineat.koomineat.domain.store.dto.response;

import com.koomineat.koomineat.domain.store.entity.Location;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "스토어 위치 응답 DTO")
public class LocationResponse {

    @Schema(description = "위치 ID", example = "3")
    private Long id;

    @Schema(description = "건물 이름", example = "예술관")
    private String name;

    public static LocationResponse from(Location l) {
        return LocationResponse.builder()
                .id(l.getId())
                .name(l.getName())
                .build();
    }
}