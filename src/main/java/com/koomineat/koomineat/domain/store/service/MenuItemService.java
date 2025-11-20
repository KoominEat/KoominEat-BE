package com.koomineat.koomineat.domain.store.service;

import com.koomineat.koomineat.domain.store.dto.response.MenuItemResponse;
import com.koomineat.koomineat.domain.store.entity.MenuItem;

import java.util.List;

public interface MenuItemService {
    List<MenuItemResponse> getMenuItems(Long storeId);
    MenuItem getMenuItemById(Long menuItemId);
}