package com.koomineat.koomineat.domain.store.service;

import com.koomineat.koomineat.domain.store.dto.response.StoreResponse;
import com.koomineat.koomineat.domain.store.entity.MenuItem;
import com.koomineat.koomineat.domain.store.entity.Store;

import java.util.List;

public interface StoreService {
    List<StoreResponse> getByCategory(Long categoryId);
    Store getStoreById(Long storeId);
}