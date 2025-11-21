package com.koomineat.koomineat.domain.store.service;

import com.koomineat.koomineat.domain.store.dto.response.StoreResponse;
import com.koomineat.koomineat.domain.store.entity.Store;
import com.koomineat.koomineat.domain.store.repository.StoreRepository;
import com.koomineat.koomineat.domain.store.specification.StoreSpecification;
import com.koomineat.koomineat.global.exception.ErrorCode;
import com.koomineat.koomineat.global.exception.KookminEatException;
import com.koomineat.koomineat.global.util.BaseUrlManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;

    @Override
    public List<StoreResponse> getStores(Long categoryId, Long locationId, HttpServletRequest request) {


        Specification<Store> spec = (root, query, cb) ->
                Stream.of(
                        categoryId == null ? null : cb.equal(root.get("category").get("id"), categoryId),
                        locationId == null ? null : cb.equal(root.get("location").get("id"), locationId)
                        )
                        .filter(Objects::nonNull)
                        .reduce(cb::and)
                        .orElse(null);


        List<Store> stores = storeRepository.findAll(spec);

        return stores.stream().map(s -> StoreResponse.from(s, BaseUrlManager.getBaseUrl(request))).toList();
    }

    @Override
    public Store getStoreById(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new KookminEatException(ErrorCode.STORE_NOT_FOUND));
    }
}