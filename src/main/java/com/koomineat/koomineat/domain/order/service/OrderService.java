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
import com.koomineat.koomineat.domain.order.repository.OrderRepository;
import com.koomineat.koomineat.domain.store.entity.MenuItem;
import com.koomineat.koomineat.domain.store.service.MenuItemService;
import com.koomineat.koomineat.domain.store.service.StoreService;
import com.koomineat.koomineat.domain.delivery.service.DeliveryService;
import com.koomineat.koomineat.global.exception.ErrorCode;
import com.koomineat.koomineat.global.exception.KookminEatException;
import com.koomineat.koomineat.global.util.BaseUrlManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    private final UserService userService;
    private final MenuItemService menuItemService;
    private final StoreService storeService;

    private final DeliveryService deliveryService;

    // order를 Id로 찾는다. (에러 처리 적용)
    private Order findByOrderId(Long orderId)
    {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new KookminEatException(ErrorCode.ORDER_NOT_FOUND));
    }

    // 만약 이미 Preparing중인 Order가 있다면 false를 리턴한다.
    private boolean hasPreparingOrder(User user)
    {
        List<Order> orders = orderRepository.findByUserIdAndStatus(user.getId(), OrderStatus.PREPARING);

        return !orders.isEmpty();
    }

    // 해당 Order의 접근 권한이 없다면 Error를, 있으면 그냥 리턴한다.
    private void checkAccessAuthority(User user, Order order) {
        if (!user.getId().equals(order.getUser().getId()))
        {
            throw new KookminEatException(ErrorCode.ORDER_ACCESS_DENIED);
        }
    }

    @Transactional
    public OrderResponse makeOrder(String authToken, OrderRequest orderRequest, HttpServletRequest request)
    {

        // user.
        User user = userService.getUserFromAuthToken(authToken);

        // 진행중인 주문이 있다면 Error.
        if(hasPreparingOrder(user))
        {
            throw new KookminEatException(ErrorCode.PENDING_ORDER_ALREADY_EXISTS);
        }

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
            order.finish();
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

        return OrderResponse.from(order, BaseUrlManager.getBaseUrl(request));
    }

    // 권한 필요
    public OrderResponse cancelOrder(String authToken, Long orderId, HttpServletRequest request)
    {
        User user = userService.getUserFromAuthToken(authToken);
        Order order = findByOrderId(orderId);
        checkAccessAuthority(user, order);
        order.setStatus(OrderStatus.CANCELED);
        return OrderResponse.from(order, BaseUrlManager.getBaseUrl(request));
    }

    // 권한 필요.
    public OrderResponse getOrder(String authToken, Long orderId, HttpServletRequest request)
    {
        User user = userService.getUserFromAuthToken(authToken);
        Order order = findByOrderId(orderId);
        return OrderResponse.from(order, BaseUrlManager.getBaseUrl(request));
    }

    // user의 order 중 status가 FINISHED 상태인 것들만 가져온다.
    // 수정: preparing과 finished만.
    public List<OrderResponse> getOrders(String authToken, HttpServletRequest request)
    {
        User user = userService.getUserFromAuthToken(authToken);
        List<Order> orders =
                orderRepository.findByUserIdAndStatus(user.getId(), OrderStatus.PREPARING);
        List<Order> finishedOrders =
                orderRepository.findByUserIdAndStatus(user.getId(), OrderStatus.FINISHED);

        orders.addAll(finishedOrders);
        return orders.stream()
                .map(od -> OrderResponse.from(od, BaseUrlManager.getBaseUrl(request)))
                .toList();
    }

    // order를 finished로 만든다.
    // 권한 필요
    public OrderResponse finishOrder(String authToken, Long orderId, HttpServletRequest request)
    {
        User user = userService.getUserFromAuthToken(authToken);
        Order order = findByOrderId(orderId);

        checkAccessAuthority(user, order);
        // set order to finished
        order.finish();
        return OrderResponse.from(order, BaseUrlManager.getBaseUrl(request));
    }
}
