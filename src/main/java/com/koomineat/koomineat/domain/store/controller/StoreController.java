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
            summary = "카테고리별 스토어 조회 (location 필터링 가능)",
            description = """
        ### 기능 설명
        - 특정 **카테고리(categoryId)** 에 속한 스토어(매장) 목록을 조회합니다.
        - 추가로, 스토어가 속한 **건물 위치(locationId)** 기준으로 필터링이 가능합니다.
        - 예: "커피 카테고리 + 예술관에 있는 매장만 조회"

        ---

        ### 제약조건
        - 인증 불필요 (누구나 조회 가능)
        - categoryId는 반드시 존재하는 카테고리여야 함
        - locationId는 null일 수 있음

        ---

        ### 예외 상황 (에러코드)
        | 상황 | ErrorCode | 설명 |
        |------|-----------|-------|
        | categoryId가 존재하지 않음 | S002 | 등록되지 않은 카테고리 |
        | locationId가 존재하지 않음 | L001 | 등록되지 않은 건물 위치 |

        (단, locationId 또는 categoryId가 없거나 유효하지 않을 경우,  
        **Back에서는 에러를 던지지 않고 빈 리스트 반환하도록 설계할 수도 있음**)  

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
              "categoryId": 1,
              "categoryName": "커피"
            },
            {
              "storeId": 2,
              "name": "카페 나무",
              "locationId": 3,
              "locationName": "예술관",
              "categoryId": 1,
              "categoryName": "커피"
            }
          ]
        }
        ```

        ---

        ### 테스트 방법
        1. DB에 StoreCategory, StoreLocation, Store 데이터가 잘 들어있는지 확인  
           (예: categoryId=1=커피, locationId=3=예술관)
        2. Swagger → GET `/stores?categoryId=1`
           - 커피 카테고리 전체 조회 확인
        3. Swagger → GET `/stores?categoryId=1&locationId=3`
           - **커피 + 예술관** 매장만 필터링되는지 확인
        4. Swagger → GET `/stores?categoryId=1&locationId=99`
           - locationId가 없으면 빈 배열([]) 반환되는지 확인
        """
    )
    @GetMapping
    public ApiResponse<List<StoreResponse>> getStores(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long locationId,
            HttpServletRequest request
    ) {
        return ApiResponse.success(storeService.getStores(categoryId, locationId, request));
    }
}