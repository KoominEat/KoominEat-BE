package com.koomineat.koomineat.domain.order.service;

import com.koomineat.koomineat.domain.auth.entity.User;
import com.koomineat.koomineat.domain.auth.repository.UserRepository;
import com.koomineat.koomineat.domain.auth.service.UserService;
import com.koomineat.koomineat.domain.order.dto.request.OrderItemRequest;
import com.koomineat.koomineat.domain.order.dto.request.OrderRequest;
import com.koomineat.koomineat.domain.order.dto.response.OrderResponse;
import com.koomineat.koomineat.domain.order.entity.Order;
import com.koomineat.koomineat.domain.order.entity.OrderItem;
import com.koomineat.koomineat.domain.order.entity.OrderStatus;
import com.koomineat.koomineat.domain.order.repository.OrderItemRepository;
import com.koomineat.koomineat.domain.order.repository.OrderRepository;
import com.koomineat.koomineat.domain.store.entity.MenuItem;
import com.koomineat.koomineat.domain.store.repository.MenuItemRepository;
import com.koomineat.koomineat.domain.store.service.MenuItemService;
import com.koomineat.koomineat.domain.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    private final UserService userService;
    private final MenuItemService menuItemService;
    private final StoreService storeService;

    public OrderResponse makeOrder(String authToken, OrderRequest orderRequest)
    {
        // user.
        User user = userService.getUserFromAuthToken(authToken);

        // Order 생성.
        Order order = Order.builder()
                .status(OrderStatus.PREPARING)
                .orderType(orderRequest.getOrderType())
                .user(user)
                .store(storeService.getStoreById(orderRequest.getStoreId()))
                .build();
        order = orderRepository.save(order);

        for(OrderItemRequest menu : orderRequest.getMenus())
        {
            Long menuItemId = menu.getMenuItemId();
            MenuItem menuItem = menuItemService.getMenuItemById(menuItemId);

            // orderItem 생성.
            OrderItem orderItem = OrderItem.builder()
                    .price(menuItem.getPrice())
                    .quantity(menu.getQuantity())
                    .order(order)
                    .menuItem(menuItem)
                    .build();
            orderItem = orderItemRepository.save(orderItem);

            // order의 price 증가.
            order.addPrice(orderItem.getPrice());
        }

        return OrderResponse.from(order);
    }

}
