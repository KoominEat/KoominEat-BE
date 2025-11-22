package com.koomineat.koomineat.global.response;

import com.koomineat.koomineat.global.util.BaseUrlManager;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class BaseImageResponse<T extends BaseImageResponse<T>> {

    protected String image;

    @SuppressWarnings("unchecked")
    protected T addBaseUrl(String baseUrl) {
        if(image != null && !image.isBlank()) {
            image = baseUrl + image;
        }
        else{
            image = baseUrl + BaseUrlManager.basicImagePath;
        }
        return (T) this;
    }
}
