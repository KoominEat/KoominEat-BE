package com.koomineat.koomineat.domain.store.dto.response;

import com.koomineat.koomineat.domain.store.entity.Store;
import com.koomineat.koomineat.global.response.BaseImageResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@Schema(description = "스토어 정보 응답 DTO")
public class StoreResponse extends BaseImageResponse<StoreResponse> {

    private Long storeId;
    private String name;

    @Schema(description = "위치 ID", example = "3")
    private Long locationId;

    @Schema(description = "위치 이름", example = "예술관")
    private String locationName;

    private Long categoryId;
    private String categoryName;

    private String backgroundImage;

    @Override
    protected StoreResponse addBaseUrl(String baseUrl) {
        super.addBaseUrl(baseUrl);
        if (backgroundImage != null && !backgroundImage.isBlank()) {
            backgroundImage = baseUrl + backgroundImage;
        }
        return this;
    }

    public static StoreResponse from(Store s) {
        return StoreResponse.builder()
                .storeId(s.getId())
                .name(s.getName())
                .locationId(s.getLocation().getId())
                .locationName(s.getLocation().getName())
                .categoryId(s.getCategory().getId())
                .categoryName(s.getCategory().getName())
                .image(s.getImage())
                .backgroundImage(s.getBackgroundImage())
                .build();
    }

    public static StoreResponse from(Store store, String baseUrl) {
        return StoreResponse.from(store).addBaseUrl(baseUrl);
    }
}