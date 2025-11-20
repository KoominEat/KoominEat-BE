package com.koomineat.koomineat.domain.store.service;

import com.koomineat.koomineat.domain.store.dto.response.StoreResponse;
import com.koomineat.koomineat.domain.store.entity.MenuItem;
import com.koomineat.koomineat.domain.store.entity.Store;
import com.koomineat.koomineat.domain.store.repository.StoreRepository;
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

    // store 객체를 id로 가져옴.
    @Override
    public Store getStoreById(Long storeId)
    {
        return storeRepository.getById(storeId);
    }
}