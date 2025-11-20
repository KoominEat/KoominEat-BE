package com.koomineat.koomineat.domain.store.controller;

import com.koomineat.koomineat.domain.store.dto.response.StoreResponse;
import com.koomineat.koomineat.domain.store.service.StoreService;
import com.koomineat.koomineat.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @Operation(
            summary = "카테고리별 스토어 조회",
            description = """
            ### 기능 설명
            - 특정 카테고리에 속한 스토어 목록을 조회합니다.
            - 매장 위치는 location(건물 이름)으로만 내려줍니다.
            - 지도 좌표(x, y)는 제공하지 않습니다. (프론트에서 처리)

            ---
            ### 성공 응답 예시
            ```json
            {
              "code": "SUCCESS",
              "message": "ok",
              "data": [
                {
                  "storeId": 1,
                  "name": "예술관 카페",
                  "location": "예술관"
                }
              ]
            }
            ```

            ---
            ### 실패 응답 예시
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
                    description = "스토어 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject("""
                            {
                              "code": "SUCCESS",
                              "message": "ok",
                              "data": [
                                {
                                  "storeId": 1,
                                  "name": "예술관 카페",
                                  "location": "예술관"
                                }
                              ]
                            }
                            """)
                    )
            )
    })
    @GetMapping
    public ApiResponse<List<StoreResponse>> getByCategory(@RequestParam Long categoryId) {
        return ApiResponse.success(storeService.getByCategory(categoryId));
    }
}