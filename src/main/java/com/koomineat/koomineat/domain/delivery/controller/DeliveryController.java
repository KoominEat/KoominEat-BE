package com.koomineat.koomineat.domain.delivery.controller;

import com.koomineat.koomineat.domain.auth.service.UserService;
import com.koomineat.koomineat.domain.delivery.dto.response.DeliveryListResponse;
import com.koomineat.koomineat.domain.delivery.service.DeliveryService;
import com.koomineat.koomineat.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    // 1. 전달 요청 전체 리스트 조회 (location 필터링)
    @Operation(
            summary = "전달 요청 전체 조회 (READY 상태, location 필터링 가능)",
            description = """
        ### 기능 설명
        - 현재 다른 사용자들이 요청한 **전달 요청(Delivery)** 중  
          **아직 전달자가 수락하지 않은 READY 상태의 요청들만 조회**합니다.
        - 전달 요청은 *사용자가 직접 입력한 배달 위치(destination)* 가 아니라,  
          **주문한 매장의 건물(StoreLocation)** 기준으로 필터링됩니다.
        - 즉, "예술관에서 주문된 전달만 보고 싶어요" 같은 기능이 가능합니다.

        ### 제약조건
        - 인증 불필요 (누구나 전달 요청 목록을 볼 수 있음)
        - 상태는 무조건 `READY`만 조회

        ---

        ### 예외 상황 (에러코드)
        - 본 API는 단순 조회 API로 **에러 없음**
        - 가게나 위치가 없으면 simply 빈 배열([]) 반환

        ---

        ### 성공 응답 예시
        ```json
        {
          "code": "SUCCESS",
          "message": "ok",
          "data": [
            {
              "deliveryId": 5,
              "status": "READY",
              "estimatedTime": 3,
              "destination": "예술관 402호",
              "message": "빠르게 부탁드립니다!",
              "order": {
                "orderId": 12,
                "orderType": "DELIVERY",
                "createdAt": "2025-11-21T03:40:00"
              },
              "deliveryUser": null
            }
          ]
        }
        ```

        ---

        ### 테스트 방법
        1. 주문 생성 API에서 `orderType=DELIVERY` 로 주문 생성
        2. 주문 생성 시 연결된 Delivery 상태는 자동으로 `READY`
        3. Swagger에서 GET `/delivery/requests` 요청
        4. locationId 없이 호출 → 전체 READY 전달 조회
        5. locationId=1 같은 값 넣고 호출 → 해당 위치에서 주문된 전달만 조회되는지 확인
        """
    )
    @GetMapping("/requests")
    public ApiResponse<List<DeliveryListResponse>> getRequests(
            @RequestParam(required = false) Long locationId
    ) {
        return ApiResponse.success(deliveryService.getRequestList(locationId));
    }

    // 2. 내가 수락한 전달 목록 조회
    @Operation(
            summary = "내가 수락한 전달 목록 조회",
            description = """
            ### 기능 설명
            - 로그인한 사용자가 **전달자(deliveryUser)** 로 참여 중인 전달 목록을 조회합니다.
            - 상태는 `DELIVERING` 또는 `FINISHED` 포함.

            ---

            ### 인증 정보
            - Cookie(`AUTH-TOKEN`)에서 authToken을 읽어 사용자 확인

            ---

            ### 제약조건
            - 로그인 필수
            - deliveryUserId = 현재 사용자

            ---

            ### 예외 코드 / 에러 상황
            | 상황 | ErrorCode | 설명 |
            |------|-----------|-------|
            | 인증 토큰 없음 | U002 | 유효하지 않은 토큰 |
            | 사용자 없음 | U001 | 탈퇴/삭제 사용자 |

            ---

            ### 성공 응답 예시
            ```json
            {
              "code": "SUCCESS",
              "message": "ok",
              "data": [
                {
                  "deliveryId": 7,
                  "status": "DELIVERING",
                  "estimatedTime": 3,
                  "destination": "북악관 305호",
                  "message": "문앞에 두고 가주세요!",
                  "order": {
                    "orderId": 22,
                    "orderType": "DELIVERY",
                    "createdAt": "2025-11-21T03:42:00"
                  },
                  "user": {
                    "userId": 10,
                    "nickname": "민지"
                  }
                }
              ]
            }
            ```

            ### 테스트 방법
            1. 전달 요청을 다른 계정으로 accept
            2. 해당 사용자로 로그인 후 GET `/delivery/my-accepted`
            3. 수락한 리스트가 정상 조회되는지 확인
            """
    )
    @GetMapping("/my-accepted")
    public ApiResponse<List<DeliveryListResponse>> getMyAccepted(
            @CookieValue(name = UserService.COOKIE_NAME, required = false) String authToken
    ) {
        return ApiResponse.success(deliveryService.getMyAcceptedList(authToken));
    }

    // 3. 내가 요청한 배달 정보 조회
    @Operation(
            summary = "내가 요청한 배달(내 주문) 조회",
            description = """
            ### 기능 설명
            - 로그인한 사용자가 주문한 DELIVERY 타입 주문에 연결된 Delivery 목록을 조회합니다.
            - 현재 요청자의 관점에서, **내가 요청한 배달들의 진행 상태**를 확인할 수 있습니다.

            ---

            ### 인증 정보
            - Cookie(`AUTH-TOKEN`)에서 사용자 인증

            ---

            ### 제약조건
            - 로그인 필수
            - order.userId = 현재 사용자

            ---

            ### 성공 응답 예시
            ```json
            {
              "code": "SUCCESS",
              "message": "ok",
              "data": [
                {
                  "deliveryId": 9,
                  "status": "READY",
                  "estimatedTime": 3,
                  "destination": "예술관 302호",
                  "message": "급해요!",
                  "order": {
                    "orderId": 33,
                    "orderType": "DELIVERY",
                    "createdAt": "2025-11-21T03:44:00"
                  },
                  "user": {
                    "userId": 15,
                    "nickname": "나(요청자)"
                  }
                }
              ]
            }
            ```

            ### 테스트 방법
            1. Delivery 타입 주문 생성
            2. GET `/delivery/my-requests`
            3. 내 주문이 정상적으로 조회되는지 확인
            """
    )
    @GetMapping("/my-requests")
    public ApiResponse<List<DeliveryListResponse>> getMyDeliveryRequests(
            @CookieValue(name = UserService.COOKIE_NAME, required = false) String authToken
    ) {
        return ApiResponse.success(deliveryService.getMyDeliveryRequests(authToken));
    }

    // 4. 전달 수락
    @Operation(
            summary = "전달 요청 수락",
            description = """
            ### 기능 설명
            - 전달자가 특정 Delivery 요청을 **수락**하는 API입니다.
            - 상태는 `READY → DELIVERING` 으로 변경됩니다.
            - 생성 후 **3분이 지나면 자동으로 PICKUP 전환 + delivery CANCELED** 처리됩니다.

            ---

            ### 인증 정보
            - Cookie(`AUTH-TOKEN`) 필수
            - 해당 사용자가 deliveryUser 로 설정됨

            ---

            ### 제약조건
            - 존재하는 deliveryId여야 함
            - READY 상태만 수락 가능
            - 자동 전환 후에는 수락 불가

            ---

            ### 예외 코드 / 에러 상황
            | 상황 | ErrorCode | 설명 |
            |------|-----------|-------|
            | 전달 없음 | D001 | Delivery 엔티티 없음 |
            | 인증 실패 | U002 | 인증 토큰 오류 |
            | 이미 시간 초과 | D003 | 자동 PICKUP 전환됨 |
            | 이미 다른 전달자가 수락함 | D004 | 중복 수락 방지 |

            ---

            ### 성공 응답 예시
            ```json
            {
              "code": "SUCCESS",
              "message": "ok",
              "data": {
                "deliveryId": 9,
                "status": "DELIVERING",
                "estimatedTime": 3,
                "destination": "예술관 302호",
                "message": "급해요!",
                "order": { ... },
                "user": { ...deliveryUser... }
              }
            }
            ```
            """
    )
    @PostMapping("/{deliveryId}/accept")
    public ApiResponse<DeliveryListResponse> accept(
            @PathVariable Long deliveryId,
            @CookieValue(name = UserService.COOKIE_NAME, required = false) String authToken
    ) {
        return ApiResponse.success(
                DeliveryListResponse.from(
                        deliveryService.acceptDelivery(authToken, deliveryId)
                )
        );
    }

    // 5. 전달 완료
    @Operation(
            summary = "전달 완료 처리",
            description = """
            ### 기능 설명
            - 전달자가 배달을 **완료했다고 표시**하는 API입니다.
            - 상태는 `DELIVERING → FINISHED` 로 변경됩니다.
            - 연결된 Order도 함께 FINISHED 로 변경됨.

            ---

            ### 제약조건
            - DELIVERING 상태만 완료 가능
            - 이미 FINISHED인 경우 재완료 불가

            ---

            ### 예외 코드 / 에러 상황
            | 상황 | ErrorCode | 설명 |
            |------|-----------|-------|
            | 전달 없음 | D001 | Delivery 엔티티 없음 |
            | 이미 완료됨 | D005 | 이미 FINISHED 상태 |

            ---

            ### 성공 응답 예시
            ```json
            {
              "code": "SUCCESS",
              "message": "ok",
              "data": {
                "deliveryId": 9,
                "status": "FINISHED",
                "destination": "예술관 302호",
                "message": "감사합니다!",
                "order": { ... },
                "user": { ... }
              }
            }
            ```
            """
    )
    @PostMapping("/{deliveryId}/finish")
    public ApiResponse<DeliveryListResponse> finish(@PathVariable Long deliveryId) {
        return ApiResponse.success(
                DeliveryListResponse.from(deliveryService.finishDelivery(deliveryId))
        );
    }
}