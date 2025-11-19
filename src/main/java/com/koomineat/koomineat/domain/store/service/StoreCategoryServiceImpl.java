package com.koomineat.koomineat.domain.store.service;

import com.koomineat.koomineat.domain.store.dto.response.StoreCategoryResponse;
import com.koomineat.koomineat.domain.store.repository.StoreCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreCategoryServiceImpl implements StoreCategoryService {

    private final StoreCategoryRepository storeCategoryRepository;

    @Override
    public List<StoreCategoryResponse> getAll() {
        return storeCategoryRepository.findAll()
                .stream()
                .map(StoreCategoryResponse::from)
                .toList();
    }
}