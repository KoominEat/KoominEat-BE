package com.koomineat.koomineat.domain.store.service;

import com.koomineat.koomineat.domain.store.dto.response.StoreResponse;
import com.koomineat.koomineat.domain.store.entity.Store;
import com.koomineat.koomineat.domain.store.repository.StoreRepository;
import com.koomineat.koomineat.global.exception.ErrorCode;
import com.koomineat.koomineat.global.exception.KookminEatException;
import com.koomineat.koomineat.global.util.BaseUrlManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;

    @Override
    public List<StoreResponse> getStores(Long categoryId, Long locationId, HttpServletRequest request) {

        List<Store> stores;

        if (locationId == null) {
            stores = storeRepository.findByCategoryId(categoryId);
        } else {
            stores = storeRepository.findByCategoryIdAndLocationId(categoryId, locationId);
        }

        return stores.stream().map(s -> StoreResponse.from(s, BaseUrlManager.getBaseUrl(request))).toList();
    }

    @Override
    public Store getStoreById(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new KookminEatException(ErrorCode.STORE_NOT_FOUND));
    }
}