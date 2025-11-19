package com.koomineat.koomineat.domain.store.controller;

import com.koomineat.koomineat.domain.store.dto.response.MenuItemResponse;
import com.koomineat.koomineat.domain.store.service.MenuItemService;
import com.koomineat.koomineat.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
                - 사용자가 특정 스토어(예: 예술관 카페)를 선택했을 때,  
                  해당 스토어에서 판매하는 **전체 메뉴 목록을 조회**하는 API입니다.
                - 메뉴 하나는 `menuId(메뉴 ID), name(메뉴 이름), price(가격)`으로 구성됩니다.

                ### 제약조건
                - `storeId`는 PathVariable로 반드시 존재해야 함
                - 유효하지 않은 스토어 ID일 경우 404 오류 발생
                - 메뉴가 없는 스토어일 경우: **200 OK + 빈 리스트([])** 반환

                ### 예외 상황 / 에러 코드
                - `MENUITEM_NOT_FOUND(M001)`  
                  - storeId는 존재하지만 메뉴가 하나도 없는 경우 (선택 사항)  
                  - (현재 로직에서는 빈 배열로 반환하는 방식이므로 필요 시 Exception 변경 가능)

                ### 성공 응답 예시
                ```json
                {
                  "code": "SUCCESS",
                  "message": "요청이 성공적으로 처리되었습니다.",
                  "data": [
                    {"menuId": 10, "name": "아메리카노", "price": 4500},
                    {"menuId": 11, "name": "카페라떼", "price": 5200}
                  ]
                }
                ```

                ### 실패 응답 예시 (스토어 없음)
                ```json
                {
                  "code": "S002",
                  "message": "가게를 찾을 수 없습니다.",
                  "data": null
                }
                ```

                ### 테스트 방법
                1. Swagger -> **GET `/stores/1/menu-items`**
                2. storeId=1에 매핑된 메뉴 목록이 표시되는지 확인
                3. 존재하지 않는 storeId로 요청 -> 404 응답 확인
                """
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "스토어 메뉴 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject("""
                            {
                              "code": "SUCCESS",
                              "message": "요청이 성공적으로 처리되었습니다.",
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
            @PathVariable Long storeId
    ) {
        return ApiResponse.success(menuItemService.getMenuItems(storeId));
    }
}