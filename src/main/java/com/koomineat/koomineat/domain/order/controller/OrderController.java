package com.koomineat.koomineat.domain.order.controller;

import com.koomineat.koomineat.domain.auth.service.UserService;
import com.koomineat.koomineat.domain.order.dto.request.OrderRequest;
import com.koomineat.koomineat.domain.order.dto.response.OrderResponse;
import com.koomineat.koomineat.domain.order.service.OrderService;
import com.koomineat.koomineat.global.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {this.orderService = orderService;}

    @PostMapping
    public ApiResponse<OrderResponse> makeOrder(@CookieValue(name = UserService.COOKIE_NAME, required = false) String authToken, @RequestBody OrderRequest orderRequest)
    {
        return ApiResponse.success(orderService.makeOrder(authToken, orderRequest));
    }

}
