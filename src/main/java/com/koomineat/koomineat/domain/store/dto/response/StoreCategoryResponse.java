package com.koomineat.koomineat.domain.store.dto.response;

import com.koomineat.koomineat.domain.store.entity.StoreCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreCategoryResponse {

    private Long categoryId;
    private String name;

    public static StoreCategoryResponse from(StoreCategory c) {
        return StoreCategoryResponse.builder()
                .categoryId(c.getId())
                .name(c.getName())
                .build();
    }
}