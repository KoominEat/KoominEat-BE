package com.koomineat.koomineat.domain.store.repository;

import com.koomineat.koomineat.domain.store.entity.StoreCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreCategoryRepository extends JpaRepository<StoreCategory, Long> {
}