package com.koomineat.koomineat.domain.store.service;

import com.koomineat.koomineat.domain.store.dto.response.MenuItemResponse;
import com.koomineat.koomineat.domain.store.entity.MenuItem;
import com.koomineat.koomineat.domain.store.repository.MenuItemRepository;
import com.koomineat.koomineat.global.exception.ErrorCode;
import com.koomineat.koomineat.global.exception.KookminEatException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;

    @Override
    public List<MenuItemResponse> getMenuItems(Long storeId) {
        return menuItemRepository.findByStoreId(storeId)
                .stream()
                .map(MenuItemResponse::from)
                .toList();
    }

    // menuItem 객체를 id로 가져옴.
    @Override
    public MenuItem getMenuItemById(Long menuItemId)
    {
        return menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new KookminEatException(ErrorCode.MENUITEM_NOT_FOUND));
    }
}