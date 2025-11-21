package com.koomineat.koomineat.domain.order.service;

import com.koomineat.koomineat.domain.auth.entity.User;
import com.koomineat.koomineat.domain.auth.service.UserService;
import com.koomineat.koomineat.domain.order.dto.request.OrderItemRequest;
import com.koomineat.koomineat.domain.order.dto.request.OrderRequest;
import com.koomineat.koomineat.domain.order.dto.response.OrderResponse;
import com.koomineat.koomineat.domain.order.entity.Order;
import com.koomineat.koomineat.domain.order.entity.OrderItem;
import com.koomineat.koomineat.domain.order.entity.OrderStatus;
import com.koomineat.koomineat.domain.order.entity.OrderType;
import com.koomineat.koomineat.domain.order.repository.OrderItemRepository;
import com.koomineat.koomineat.domain.order.repository.OrderRepository;
import com.koomineat.koomineat.domain.store.entity.MenuItem;
import com.koomineat.koomineat.domain.store.service.MenuItemService;
import com.koomineat.koomineat.domain.store.service.StoreService;
import com.koomineat.koomineat.domain.delivery.service.DeliveryService;
import jakarta.transaction.Transactional;
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

    private final DeliveryService deliveryService;

    @Transactional
    public OrderResponse makeOrder(String authToken, OrderRequest orderRequest)
    {
        // user.
        User user = userService.getUserFromAuthToken(authToken);

        // Order 생성.
        Order order = Order.builder()
                // default로 Preparing으로 설정.
                .status(OrderStatus.PREPARING)
                .orderType(orderRequest.getOrderType())
                .store(storeService.getStoreById(orderRequest.getStoreId()))
                .build();

        for(OrderItemRequest menu : orderRequest.getMenus())
        {
            MenuItem menuItem = menuItemService.getMenuItemById(menu.getMenuItemId());

            // orderItem 생성.
            OrderItem orderItem = OrderItem.builder()
                    .price(menuItem.getPrice())
                    .quantity(menu.getQuantity())
                    .menuItem(menuItem)
                    .build();
            order.addOrderItem(orderItem);
        }

        // 만약 OrderType이 Pickup이라면 바로 Finish.
        if(order.getOrderType() == OrderType.PICKUP)
        {
            order.setStatus(OrderStatus.FINISHED);
        }
        else if(order.getOrderType() == OrderType.DELIVERY)
        {
            // delivery 저장 전 먼저 order save.
            order = orderRepository.save(order);
            deliveryService.createDelivery(
                    order,
                    orderRequest.getDestination(),   // 전달 위치
                    orderRequest.getMessage()        // 한줄 메시지
            );
        }

        // user list에 order를 추가.
        user.addOrder(order);
        order = orderRepository.save(order);

        return OrderResponse.from(order);
    }

    public OrderResponse getOrder(String authToken, Long orderId)
    {
        User user = userService.getUserFromAuthToken(authToken);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return OrderResponse.from(order);
    }

    // status가 FINISHED 상태인 것들만 가져온다.
    public List<OrderResponse> getFinishedOrders(String authToken)
    {
        User user = userService.getUserFromAuthToken(authToken);
        List<Order> finishedOrders =
                orderRepository.findByUserIdAndStatus(user.getId(), OrderStatus.FINISHED);

        return finishedOrders.stream()
                .map(OrderResponse::from)
                .toList();
    }

    // order를 finished로 만든다.
    public OrderResponse finishOrder(String authToken, Long orderId)
    {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // set order to finished
        order.setStatus(OrderStatus.FINISHED);
        return OrderResponse.from(order);
    }
}
