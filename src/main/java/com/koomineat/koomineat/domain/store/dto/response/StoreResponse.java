package com.koomineat.koomineat.domain.store.dto.response;

import com.koomineat.koomineat.domain.store.entity.MenuItem;
import com.koomineat.koomineat.domain.store.entity.Store;
import com.koomineat.koomineat.global.response.BaseImageResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@Schema(description = "스토어 정보 응답 DTO")
public class StoreResponse extends BaseImageResponse<StoreResponse> {

    @Schema(description = "스토어 ID", example = "1")
    private Long storeId;

    @Schema(description = "스토어 이름", example = "예술관 카페")
    private String name;

    @Schema(description = "스토어 위치(건물 이름)", example = "예술관")
    private String location;

    private String backgroundImage;

    @Override
    protected StoreResponse addBaseUrl(String baseUrl) {
        super.addBaseUrl(baseUrl);
        if (backgroundImage != null && !backgroundImage.isBlank()) {
            backgroundImage = baseUrl + backgroundImage;
        }
        return this;
    }

    public static StoreResponse from(Store store) {
        return StoreResponse.builder()
                .storeId(store.getId())
                .name(store.getName())
                .location(store.getLocation())
                .image(store.getImage())
                .backgroundImage(store.getBackgroundImage())
                .build();
    }

    public static StoreResponse from(Store store, String baseUrl) {
        return StoreResponse.from(store).addBaseUrl(baseUrl);
    }
}