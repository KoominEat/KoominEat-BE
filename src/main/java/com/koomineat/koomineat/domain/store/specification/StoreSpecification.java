package com.koomineat.koomineat.domain.store.specification;

import com.koomineat.koomineat.domain.store.entity.Store;
import org.springframework.data.jpa.domain.Specification;

public class StoreSpecification {

    public static Specification<Store> hasCategory(Long categoryId) {
        return (root, query, cb) ->
                categoryId == null ? null :
                        cb.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Store> hasLocation(Long locationId) {
        return (root, query, cb) ->
                locationId == null ? null :
                        cb.equal(root.get("location").get("id"), locationId);
    }
}
