package com.koomineat.koomineat.domain.store.repository;

import com.koomineat.koomineat.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {

    List<Store> findByCategoryId(Long categoryId);

    List<Store> findByCategoryIdAndLocationId(Long categoryId, Long locationId);
}