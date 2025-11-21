package com.koomineat.koomineat.domain.store.controller;

import com.koomineat.koomineat.domain.store.dto.response.LocationResponse;
import com.koomineat.koomineat.domain.store.dto.response.StoreCategoryResponse;
import com.koomineat.koomineat.domain.store.service.LocationService;
import com.koomineat.koomineat.domain.store.service.StoreCategoryService;
import com.koomineat.koomineat.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping
    public ApiResponse<List<LocationResponse>> getAll() {
        return ApiResponse.success(locationService.getAllLocations());
    }
}