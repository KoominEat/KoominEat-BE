package com.koomineat.koomineat.domain.delivery.controller;

import com.koomineat.koomineat.domain.auth.service.UserService;
import com.koomineat.koomineat.domain.delivery.dto.response.DeliveryResponse;
import com.koomineat.koomineat.domain.delivery.service.DeliveryService;
import com.koomineat.koomineat.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    // ------------------------
    // 1) 전달 수락 API
    // ------------------------
    @Operation(
            summary = "전달 요청 수락",
            description = """
            ### 기능 설명
            - 전달자가 특정 전달 요청을 **수락**하는 API입니다.
            - 수락 후 상태는 `READY → DELIVERING` 으로 변경됩니다.
            - 전달자가 수락하기 전에 **1분이 지나면 자동으로 CANCELED + PICKUP 전환**됩니다.

            ---

            ### 인증 정보
            - 인증 토큰은 Cookie(`AUTH-TOKEN`)에서 가져옵니다.
            - 전달을 수락한 사용자는 `deliveryUserId`에 저장됩니다.

            ---

            ### 제약조건
            - `deliveryId`는 PathVariable 필수값
            - 이미 수락된 전달은 다시 수락할 수 없음
            - 전달 요청 생성 후 1분이 지나 수락하면 자동으로 PICKUP 처리됨

            ---

            ### 예외 코드 / 에러 상황
            | 상황 | ErrorCode | 설명 |
            |-----|-----------|------|
            | 존재하지 않는 전달 ID | DELIVERY_NOT_FOUND | 전달 엔티티 없음 |
            | 인증 토큰 없음 | AUTH_REQUIRED | 로그인 필요 |
            | 전달 수락 가능 시간(1분) 초과 | DELIVERY_TIMEOUT | 자동 PICKUP 전환 |
            
            ---

            ### 성공 응답 예시
            ```json
            {
              "code": "SUCCESS",
              "message": "ok",
              "data": {
                "deliveryId": 3,
                "orderId": 12,
                "status": "DELIVERING",
                "destination": "북악관 305호",
                "message": "문 앞에 두고 가주세요",
                "deliveryUserId": 7
              }
            }
            ```

            ### 실패 응답 예시 (전달 없음)
            ```json
            {
              "code": "D001",
              "message": "전달 정보를 찾을 수 없습니다.",
              "data": null
            }
            ```

            ### 실패 응답 예시 (수락 시간 초과)
            ```json
            {
              "code": "D003",
              "message": "전달 수락 가능 시간이 초과되어 자동으로 PICKUP 전환되었습니다.",
              "data": {
                "deliveryId": 3,
                "status": "CANCELED"
              }
            }
            ```

            ---

            ### 테스트 방법
            1. 주문 생성(DELIVERY 타입으로)
            2. Swagger → `POST /delivery/{deliveryId}/accept`
            3. Cookie에 AUTH-TOKEN 값 넣어서 요청
            4. 상태가 `DELIVERING`으로 바뀌는지 확인
            5. 생성 후 1분 지나 테스트 하면 자동 `CANCELED` 확인 가능
            """
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "전달 수락 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject("""
                            {
                              "code": "SUCCESS",
                              "message": "ok",
                              "data": {
                                "deliveryId": 3,
                                "orderId": 12,
                                "status": "DELIVERING",
                                "destination": "예술관 103호",
                                "message": "빨리 부탁드려요!",
                                "deliveryUserId": 5
                              }
                            }
                            """)
                    )
            )
    })
    @PostMapping("/{deliveryId}/accept")
    public ApiResponse<DeliveryResponse> accept(
            @PathVariable Long deliveryId,
            @CookieValue(name = UserService.COOKIE_NAME, required = false) String authToken
    ) {
        return ApiResponse.success(
                DeliveryResponse.from(
                        deliveryService.acceptDelivery(authToken, deliveryId)
                )
        );
    }

    // ------------------------
    // 2) 전달 완료 API
    // ------------------------
    @Operation(
            summary = "전달 완료 처리",
            description = """
            ### 기능 설명
            - 전달자가 전달을 **완료했다고 표시**하는 API입니다.
            - 상태는 `DELIVERING → FINISHED` 로 변경됩니다.
            - 이어서 연결된 Order도 `OrderStatus.FINISHED`로 처리됩니다.

            ---

            ### 제약조건
            - 이미 완료된 전달은 다시 완료 처리할 수 없음
            - 존재하지 않는 deliveryId 요청 시 에러

            ---

            ### 예외 코드 / 에러 상황
            | 상황 | ErrorCode | 설명 |
            |-----|-----------|------|
            | 전달 ID 없음 | DELIVERY_NOT_FOUND | 전달 레코드 존재 X |
            | 이미 완료된 전달 | DELIVERY_ALREADY_FINISHED | 상태 중복 처리 |

            ---

            ### 성공 응답 예시
            ```json
            {
              "code": "SUCCESS",
              "message": "ok",
              "data": {
                "deliveryId": 3,
                "orderId": 12,
                "status": "FINISHED",
                "destination": "북악관 305호",
                "message": "감사합니다!",
                "deliveryUserId": 7
              }
            }
            ```

            ### 실패 응답 예시
            ```json
            {
              "code": "D001",
              "message": "전달 정보를 찾을 수 없습니다.",
              "data": null
            }
            ```

            ---

            ### 테스트 방법
            1. 전달 수락 API 먼저 실행 (`/delivery/{id}/accept`)
            2. 그 다음 Swagger → `POST /delivery/{deliveryId}/finish`
            3. 상태가 `FINISHED` 로 변경되는지 확인
            """
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "전달 완료 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject("""
                            {
                              "code": "SUCCESS",
                              "message": "ok",
                              "data": {
                                "deliveryId": 3,
                                "orderId": 12,
                                "status": "FINISHED",
                                "destination": "예술관 103호",
                                "message": "감사합니다!",
                                "deliveryUserId": 5
                              }
                            }
                            """)
                    )
            )
    })
    @PostMapping("/{deliveryId}/finish")
    public ApiResponse<DeliveryResponse> finish(@PathVariable Long deliveryId) {
        return ApiResponse.success(
                DeliveryResponse.from(deliveryService.finishDelivery(deliveryId))
        );
    }
}