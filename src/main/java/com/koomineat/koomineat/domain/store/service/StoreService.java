package com.koomineat.koomineat.domain.store.service;

import com.koomineat.koomineat.domain.store.dto.response.StoreResponse;

import java.util.List;

public interface StoreService {
    List<StoreResponse> getByCategory(Long categoryId);
}