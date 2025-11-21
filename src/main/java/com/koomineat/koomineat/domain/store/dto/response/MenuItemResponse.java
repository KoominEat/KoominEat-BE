package com.koomineat.koomineat.domain.store.dto.response;

import com.koomineat.koomineat.domain.store.entity.MenuItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "메뉴 조회 응답 DTO")
public class MenuItemResponse {

    @Schema(description = "메뉴 ID", example = "1")
    private Long menuId;

    @Schema(description = "메뉴 이름", example = "아메리카노")
    private String name;

    @Schema(description = "가격", example = "4500")
    private Integer price;

    public static MenuItemResponse from(MenuItem m) {
        return MenuItemResponse.builder()
                .menuId(m.getId())
                .name(m.getName())
                .price(m.getPrice())
                .build();
    }
}