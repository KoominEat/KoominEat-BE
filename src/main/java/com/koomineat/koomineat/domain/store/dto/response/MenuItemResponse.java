package com.koomineat.koomineat.domain.store.dto.response;

import com.koomineat.koomineat.domain.store.entity.MenuItem;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuItemResponse {

    private Long menuId;
    private String name;
    private Integer price;
    private String image;

    public static MenuItemResponse from(MenuItem m) {
        return MenuItemResponse.builder()
                .menuId(m.getId())
                .name(m.getName())
                .price(m.getPrice())
                .image(m.getImage())
                .build();
    }
}