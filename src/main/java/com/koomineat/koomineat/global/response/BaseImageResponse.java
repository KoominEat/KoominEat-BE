package com.koomineat.koomineat.global.response;

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
    protected T addBaseUrl(String baseUrl)
    {
        if(image != null && !image.isBlank())
        {
            image = baseUrl + image;
        }
        return (T) this;
    }
}
