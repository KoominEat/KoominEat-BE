package com.koomineat.koomineat.domain.store.service;

import com.koomineat.koomineat.domain.store.dto.response.StoreCategoryResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface StoreCategoryService {
    List<StoreCategoryResponse> getAll(HttpServletRequest request);
}