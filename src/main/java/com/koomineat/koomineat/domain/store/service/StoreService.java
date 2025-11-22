package com.koomineat.koomineat.domain.store.service;

import com.koomineat.koomineat.domain.store.dto.response.StoreResponse;
import com.koomineat.koomineat.domain.store.entity.Store;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface StoreService {
    List<StoreResponse> getStores(Long categoryId, Long locationId, HttpServletRequest request);
    StoreResponse getStore(Long storeId, HttpServletRequest request);
    Store getStoreById(Long storeId);
}