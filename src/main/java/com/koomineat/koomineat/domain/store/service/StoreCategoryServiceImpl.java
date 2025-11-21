package com.koomineat.koomineat.domain.store.service;

import com.koomineat.koomineat.domain.store.dto.response.StoreCategoryResponse;
import com.koomineat.koomineat.domain.store.repository.StoreCategoryRepository;
import com.koomineat.koomineat.global.util.BaseUrlManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreCategoryServiceImpl implements StoreCategoryService {

    private final StoreCategoryRepository storeCategoryRepository;

    @Override
    public List<StoreCategoryResponse> getAll(HttpServletRequest request) {
        return storeCategoryRepository.findAll()
                .stream()
                .map(c -> StoreCategoryResponse.from(c, BaseUrlManager.getBaseUrl(request)))
                .toList();
    }
}