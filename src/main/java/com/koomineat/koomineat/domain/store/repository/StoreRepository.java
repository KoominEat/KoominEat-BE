package com.koomineat.koomineat.domain.store.repository;

import com.koomineat.koomineat.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long>, JpaSpecificationExecutor<Store> {

    List<Store> findByCategoryId(Long categoryId);

    List<Store> findByCategoryIdAndLocationId(Long categoryId, Long locationId);
}