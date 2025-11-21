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
    private final UserService userService;
    private final OrderService orderService;

    public OrderController(OrderService orderService, UserService userService)
    {
        this.userService = userService;
        this.orderService = orderService;
    }

    // 단일 get
    @GetMapping("/{orderId}")
    public ApiResponse<OrderResponse> getOrder(
            @PathVariable Long orderId,
            @CookieValue(name = UserService.COOKIE_NAME, required = false) String authToken
    )
    {
        if(userService.authenticateByCookie(authToken))
            return ApiResponse.success(orderService.getOrder(authToken, orderId));
        else
            return ApiResponse.fail("Can't find user", "User가 없거나 인증이 실패했습니다.");
    }


    /*
    예시 response:
    {
    "code": "SUCCESS",
    "message": "요청이 성공적으로 처리되었습니다.",
    "data": [
        {
            "orderId": 11,
            "status": "FINISHED",
            "totalPrice": 16700,
            "orderItemResponses": [
                {
                    "menuItemResponse": {
                        "menuId": 3,
                        "name": "바닐라 라떼",
                        "price": 6100
                    },
                    "quantity": 2
                },
                {
                    "menuItemResponse": {
                        "menuId": 6,
                        "name": "아메리카노",
                        "price": 4500
                    },
                    "quantity": 1
                }
            ],
            "orderType": "PICKUP",
            "storeResponse": {
                "storeId": 1,
                "name": "카페-K",
                "location": "본부관",
                "x": 0.0,
                "y": 0.0
            }
        },
        {
            "orderId": 12,
            "status": "FINISHED",
            "totalPrice": 35000,
            "orderItemResponses": [
                {
                    "menuItemResponse": {
                        "menuId": 2,
                        "name": "카페모카",
                        "price": 5700
                    },
                    "quantity": 4
                },
                {
                    "menuItemResponse": {
                        "menuId": 1,
                        "name": "카라멜 마키야또",
                        "price": 6100
                    },
                    "quantity": 2
                }
            ],
            "orderType": "PICKUP",
            "storeResponse": {
                "storeId": 1,
                "name": "카페-K",
                "location": "본부관",
                "x": 0.0,
                "y": 0.0
            }
        }
    ]
}
     */
    @GetMapping("/finished")
    public ApiResponse<List<OrderResponse>> getFinishedOrders(@CookieValue(name = UserService.COOKIE_NAME, required = false) String authToken)
    {
        return ApiResponse.success(orderService.getFinishedOrders(authToken));
    }

    // 해당 Order를 완료 상태로 만든다. (필요 없을수도 있음.)
    @PatchMapping("/finish/{orderId}")
    public ApiResponse<OrderResponse> finishOrder(
            @PathVariable Long orderId,
            @CookieValue(name = UserService.COOKIE_NAME, required = false) String authToken
    )
    {
        return ApiResponse.success(orderService.finishOrder(authToken, orderId));
    }

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
    // 주문 진행.
    @PostMapping
    public ApiResponse<OrderResponse> makeOrder(@CookieValue(name = UserService.COOKIE_NAME, required = false) String authToken, @RequestBody OrderRequest orderRequest)
    {
        return ApiResponse.success(orderService.makeOrder(authToken, orderRequest));
    }

    @PostMapping("/cancel/{orderId}")
    public ApiResponse<OrderResponse> cancelOrder(
            @PathVariable Long orderId,
            @CookieValue(name = UserService.COOKIE_NAME, required = false) String authToken
    )
    {
        return ApiResponse.success(orderService.cancelOrder(authToken, orderId));
    }

}
