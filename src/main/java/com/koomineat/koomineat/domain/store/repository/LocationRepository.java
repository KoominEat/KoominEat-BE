package com.koomineat.koomineat.domain.store.repository;

import com.koomineat.koomineat.domain.store.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}