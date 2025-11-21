package com.koomineat.koomineat.domain.store.service;

import com.koomineat.koomineat.domain.store.dto.response.LocationResponse;
import com.koomineat.koomineat.domain.store.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    public List<LocationResponse> getAllLocations() {
        return locationRepository.findAll()
                .stream()
                .map(LocationResponse::from)
                .toList();
    }
}