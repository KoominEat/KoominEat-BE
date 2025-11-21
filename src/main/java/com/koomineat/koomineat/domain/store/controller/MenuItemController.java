package com.koomineat.koomineat.domain.store.controller;

import com.koomineat.koomineat.domain.store.dto.response.MenuItemResponse;
import com.koomineat.koomineat.domain.store.service.MenuItemService;
import com.koomineat.koomineat.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stores/{storeId}/menu-items")
@RequiredArgsConstructor
public class MenuItemController {

    private final MenuItemService menuItemService;

    @Operation(
            summary = "특정 스토어의 메뉴 전체 조회",
            description = """
                ### 기능 설명
                - 특정 스토어에서 판매하는 **전체 메뉴 목록을 조회**합니다.

                ### 제약조건
                - `storeId`: PathVariable (필수)
                - 존재하지 않는 storeId → `STORE_NOT_FOUND(S002)` 반환
                - 메뉴가 없으면: 200 OK + 빈 배열

                ---
                ### 성공 응답 (예시)
                ```json
                {
                  "code": "SUCCESS",
                  "message": "요청이 성공적으로 처리되었습니다.",
                  "data": [
                    {"menuId": 1, "name": "아메리카노", "price": 4500}
                  ]
                }
                ```

                ---
                ### 실패 응답 (스토어 없음)
                ```json
                {
                  "code": "S002",
                  "message": "가게를 찾을 수 없습니다.",
                  "data": null
                }
                ```
                """
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "메뉴 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject("""
                            {
                              "code": "SUCCESS",
                              "message": "ok",
                              "data": [
                                {"menuId": 1, "name": "아메리카노", "price": 4500}
                              ]
                            }
                            """)
                    )
            )
    })
    @GetMapping
    public ApiResponse<List<MenuItemResponse>> getMenuItems(
            @PathVariable Long storeId,
            HttpServletRequest request
    ) {
        return ApiResponse.success(menuItemService.getMenuItems(storeId, request));
    }
}