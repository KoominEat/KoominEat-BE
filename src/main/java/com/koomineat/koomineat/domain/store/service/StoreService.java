package com.koomineat.koomineat.domain.store.service;

import com.koomineat.koomineat.domain.store.dto.response.StoreResponse;
import com.koomineat.koomineat.domain.store.entity.Store;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface StoreService {
    List<StoreResponse> getByCategory(Long categoryId, HttpServletRequest request);
    Store getStoreById(Long storeId);
}