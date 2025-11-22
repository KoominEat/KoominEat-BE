package com.koomineat.koomineat.domain.store.service;

import com.koomineat.koomineat.domain.store.dto.response.MenuItemResponse;
import com.koomineat.koomineat.domain.store.entity.MenuItem;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface MenuItemService {
    List<MenuItemResponse> getMenuItems(Long storeId, HttpServletRequest request);
    MenuItem getMenuItemById(Long menuItemId);
}