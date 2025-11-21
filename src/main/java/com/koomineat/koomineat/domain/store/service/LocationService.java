package com.koomineat.koomineat.domain.store.service;

import com.koomineat.koomineat.domain.store.dto.response.LocationResponse;

import java.util.List;

public interface LocationService {

    List<LocationResponse> getAllLocations();
}
