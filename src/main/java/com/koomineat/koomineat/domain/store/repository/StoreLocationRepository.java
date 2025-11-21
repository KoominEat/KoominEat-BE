package com.koomineat.koomineat.domain.store.repository;

import com.koomineat.koomineat.domain.store.entity.StoreLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreLocationRepository extends JpaRepository<StoreLocation, Long> {
}