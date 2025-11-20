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
    /*
    request 예시:
    {
      "storeId": 1,
      "orderType": "DELIVERY",
      "menus": [
        {
          "menuItemId": 3,
          "quantity": 2
        },
        {
          "menuItemId": 6,
          "quantity": 1
        }
      ]
    }

response 예시:
    {
    "code": "SUCCESS",
    "message": "요청이 성공적으로 처리되었습니다.",
    "data": {
        "orderId": 2,
        "status": "PREPARING",
        "totalPrice": 10600,
        "orderType": "DELIVERY",
        "storeResponse": {
            "storeId": 1,
            "name": "카페-K",
            "location": "본부관",
            "x": 0.0,
            "y": 0.0
        }
    }
}
*/

    @PostMapping
    public ApiResponse<OrderResponse> makeOrder(@CookieValue(name = UserService.COOKIE_NAME, required = false) String authToken, @RequestBody OrderRequest orderRequest)
    {
        return ApiResponse.success(orderService.makeOrder(authToken, orderRequest));
    }

}
