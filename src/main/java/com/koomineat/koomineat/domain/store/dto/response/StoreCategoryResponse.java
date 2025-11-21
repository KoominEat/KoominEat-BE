package com.koomineat.koomineat.domain.store.dto.response;

import com.koomineat.koomineat.domain.store.entity.StoreCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "스토어 카테고리 응답 DTO")
public class StoreCategoryResponse {

    @Schema(description = "카테고리 ID", example = "1")
    private Long categoryId;

    @Schema(description = "카테고리 이름", example = "커피")
    private String name;

    public static StoreCategoryResponse from(StoreCategory c) {
        return StoreCategoryResponse.builder()
                .categoryId(c.getId())
                .name(c.getName())
                .build();
    }
}