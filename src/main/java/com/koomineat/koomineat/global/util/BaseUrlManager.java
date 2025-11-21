package com.koomineat.koomineat.global.util;

import jakarta.servlet.http.HttpServletRequest;

public class BaseUrlManager {

    public static final String basicImagePath = "/cat.png";

    public static String getBaseUrl(HttpServletRequest request)
    {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

        return baseUrl;
    }

}
