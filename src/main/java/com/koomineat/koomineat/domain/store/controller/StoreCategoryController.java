package com.koomineat.koomineat.domain.store.controller;

import com.koomineat.koomineat.domain.store.dto.response.StoreCategoryResponse;
import com.koomineat.koomineat.domain.store.service.StoreCategoryService;
import com.koomineat.koomineat.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/store-categories")
@RequiredArgsConstructor
public class StoreCategoryController {

    private final StoreCategoryService storeCategoryService;

    @Operation(
            summary = "스토어 카테고리 조회",
            description = """
                ### 기능 설명
                - 서비스에서 제공하는 스토어 분류(Category)를 모두 조회합니다.
                - 예: 커피 / 버거 / 디저트 / 한식 / 분식 등

                ### 제약조건
                - 없음 (쿼리 파라미터 불필요)

                ### 성공 응답 예시
                ```json
                {
                  "code": "SUCCESS",
                  "message": "요청이 성공적으로 처리되었습니다.",
                  "data": [
                    {"categoryId": 1, "name": "커피"},
                    {"categoryId": 2, "name": "버거"}
                  ]
                }
                ```

                ### 테스트 방법
                1. Swagger -> GET `/store-categories`
                2. 카테고리 목록 배열 확인
                """
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "카테고리 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject("""
                            {
                              "code": "SUCCESS",
                              "message": "요청이 성공적으로 처리되었습니다.",
                              "data": [
                                {"categoryId": 1, "name": "커피"},
                                {"categoryId": 2, "name": "버거"}
                              ]
                            }
                            """)
                    )
            )
    })
    @GetMapping
    public ApiResponse<List<StoreCategoryResponse>> getAll() {
        return ApiResponse.success(storeCategoryService.getAll());
    }
}