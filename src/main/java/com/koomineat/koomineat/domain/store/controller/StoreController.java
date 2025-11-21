package com.koomineat.koomineat.domain.store.controller;

import com.koomineat.koomineat.domain.store.dto.response.StoreResponse;
import com.koomineat.koomineat.domain.store.service.StoreService;
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
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @Operation(
            summary = "스토어 조회 (카테고리 + 위치 필터링 지원)",
            description = """
            ### 기능 설명
            - 특정 카테고리에 속한 스토어를 조회합니다.
            - `locationId` 를 전달하면 해당 건물에 있는 스토어만 필터링합니다.
            - `locationId` 없이 호출하면 해당 카테고리 전체 스토어 반환.

            ---
            ### 요청 예시
            - `/stores?categoryId=1` → 커피 카테고리 전체
            - `/stores?categoryId=1&locationId=3` → 커피 + 예술관 매장만

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
                  "locationId": 3,
                  "locationName": "예술관",
                  "image": "https://server/image/xxx",
                  "backgroundImage": "https://server/image/yyy"
                }
              ]
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
                                  "locationId": 3,
                                  "locationName": "예술관"
                                }
                              ]
                            }
                            """)
                    )
            )
    })
    @GetMapping
    public ApiResponse<List<StoreResponse>> getStores(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long locationId,
            HttpServletRequest request
    ) {
        return ApiResponse.success(storeService.getStores(categoryId, locationId, request));
    }
}