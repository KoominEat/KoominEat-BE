package com.koomineat.koomineat.domain.store.repository;

import com.koomineat.koomineat.domain.store.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findByStoreId(Long storeId);
}