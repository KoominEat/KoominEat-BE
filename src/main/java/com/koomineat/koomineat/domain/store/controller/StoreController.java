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
                - 특정 카테고리(예: 커피)에 해당하는 모든 스토어 목록을 조회합니다.
                - 각 스토어의 위치 정보는 다음과 같습니다:
                  - `location` : 건물 이름 (예술관, 공학관)
                  - `x`, `y` : 지도 위 표시용 좌표(0~1 float)

                ### 제약조건
                - `categoryId`는 필수 QueryParam
                - 존재하지 않는 카테고리일 경우:
                  - 스토어가 하나도 없다면 `STORE_NOT_FOUND (S002)` 반환

                ### 성공 응답 예시
                ```json
                {
                  "code": "SUCCESS",
                  "message": "요청이 성공적으로 처리되었습니다.",
                  "data": [
                    {
                      "storeId": 1,
                      "name": "예술관 카페",
                      "location": "예술관",
                      "x": 0.35,
                      "y": 0.48
                    }
                  ]
                }
                ```

                ### 실패 응답 예시
                ```json
                {
                  "code": "S002",
                  "message": "가게를 찾을 수 없습니다.",
                  "data": null
                }
                ```

                ### 테스트 방법
                1. Swagger -> GET `/stores?categoryId=1`
                2. 프론트 측 지도 좌표(x,y) 기반 렌더링 가능 여부 확인
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
                              "message": "요청이 성공적으로 처리되었습니다.",
                              "data": [
                                {
                                  "storeId": 1,
                                  "name": "예술관 카페",
                                  "location": "예술관",
                                  "x": 0.35,
                                  "y": 0.48
                                }
                              ]
                            }
                            """)
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "STORE_NOT_FOUND",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject("""
                            {
                              "code": "S002",
                              "message": "가게를 찾을 수 없습니다.",
                              "data": null
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