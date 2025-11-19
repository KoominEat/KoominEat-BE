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
    private Double x;
    private Double y;

    public static StoreResponse from(Store s) {
        return StoreResponse.builder()
                .storeId(s.getId())
                .name(s.getName())
                .location(s.getLocation())
                .x(s.getX())
                .y(s.getY())
                .build();
    }
}