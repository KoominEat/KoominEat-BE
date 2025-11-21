package com.koomineat.koomineat.domain.store.service;

import com.koomineat.koomineat.domain.store.dto.response.StoreResponse;
import com.koomineat.koomineat.domain.store.entity.Store;
import com.koomineat.koomineat.domain.store.repository.StoreRepository;
import com.koomineat.koomineat.global.exception.ErrorCode;
import com.koomineat.koomineat.global.exception.KookminEatException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;

    @Override
    public List<StoreResponse> getByCategory(Long categoryId) {
        return storeRepository.findByCategoryId(categoryId)
                .stream()
                .map(StoreResponse::from)
                .toList();
    }

    @Override
    public Store getStoreById(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new KookminEatException(ErrorCode.STORE_NOT_FOUND));
    }
}