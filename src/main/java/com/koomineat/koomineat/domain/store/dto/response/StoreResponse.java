package com.koomineat.koomineat.domain.store.dto.response;

import com.koomineat.koomineat.domain.store.entity.Store;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreResponse {

    private Long storeId;
    private String name;
    private String location;

    public static StoreResponse from(Store store) {
        return StoreResponse.builder()
                .storeId(store.getId())
                .name(store.getName())
                .location(store.getLocation())
                .build();
    }
}