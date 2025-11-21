package com.koomineat.koomineat.domain.delivery.service;

import com.koomineat.koomineat.domain.auth.entity.User;
import com.koomineat.koomineat.domain.auth.service.UserService;
import com.koomineat.koomineat.domain.delivery.entity.Delivery;
import com.koomineat.koomineat.domain.delivery.entity.DeliveryStatus;
import com.koomineat.koomineat.domain.delivery.repository.DeliveryRepository;
import com.koomineat.koomineat.domain.order.entity.Order;
import com.koomineat.koomineat.domain.order.entity.OrderStatus;
import com.koomineat.koomineat.domain.order.entity.OrderType;
import com.koomineat.koomineat.global.exception.ErrorCode;
import com.koomineat.koomineat.global.exception.KookminEatException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final UserService userService;

    @Override
    @Transactional
    public Delivery createDelivery(Order order, String destination, String message) {
        Delivery delivery = Delivery.builder()
                .order(order)
                .destination(destination)
                .message(message)
                .status(DeliveryStatus.READY)
                .build();

        return deliveryRepository.save(delivery);
    }

    @Override
    @Transactional
    public Delivery acceptDelivery(String authToken, Long deliveryId) {

        // 1. 전달 요청 조회
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new KookminEatException(ErrorCode.DELIVERY_NOT_FOUND));

        Order order = delivery.getOrder();

        // 2. 유저 조회 (쿠키 기반)
        User user = userService.getUserFromAuthToken(authToken);

        // 3. 상태에 따른 방어 로직들

        // 이미 완료된 전달이면 수락 불가
        if (delivery.getStatus() == DeliveryStatus.FINISHED) {
            throw new KookminEatException(ErrorCode.DELIVERY_ALREADY_FINISHED);
        }

        // 이미 취소된 전달이면 수락 불가
        if (delivery.getStatus() == DeliveryStatus.CANCELED) {
            throw new KookminEatException(ErrorCode.DELIVERY_ALREADY_CANCELED);
        }

        // 이미 누군가 DELIVERING 상태로 수락한 전달이면, 중복 수락 방지
        if (delivery.getStatus() == DeliveryStatus.DELIVERING) {
            // 다른 사람이 이미 잡은 경우
            if (delivery.getDeliveryUser() != null
                    && !delivery.getDeliveryUser().getId().equals(user.getId())) {
                throw new KookminEatException(ErrorCode.DELIVERY_ALREADY_ACCEPTED);
            }
            // 같은 유저가 다시 호출하면 그냥 현재 상태 그대로 반환
            return delivery;
        }

        // 4. 수락 가능 시간(예: 생성 후 1분) 초과 체크
        if (order.getCreatedAt().plusMinutes(1).isBefore(LocalDateTime.now())) {
            // 시간 초과 → 자동 PICKUP 전환 + 전달 취소
            // changeOrderType으로 자동으로 Finished로 전환.
            order.changeOrderType(OrderType.PICKUP);
            delivery.updateStatus(DeliveryStatus.CANCELED);
            // 여기서는 에러로 던지지 않고, "취소된 상태" 그대로 반환
            // (프론트에서 status 보고 '시간 초과로 자동 취소' 같은 메시지 띄울 수 있음)
            return delivery;
            // 만약 여기서 D003(DELIVERY_TIMEOUT)을 쓰고 싶다면:
            // throw new KookminEatException(ErrorCode.DELIVERY_TIMEOUT);
        }

        // 5. 정상 수락 처리
        delivery.updateDeliveryUser(user);
        delivery.updateStatus(DeliveryStatus.DELIVERING);

        return delivery;
    }

    @Override
    @Transactional
    public Delivery finishDelivery(Long deliveryId) {

        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new KookminEatException(ErrorCode.DELIVERY_NOT_FOUND));

        // 이미 취소된 요청이면 완료 처리 불가
        if (delivery.getStatus() == DeliveryStatus.CANCELED) {
            throw new KookminEatException(ErrorCode.DELIVERY_ALREADY_CANCELED);
        }

        // 이미 완료된 요청이면 중복 완료 방지
        if (delivery.getStatus() == DeliveryStatus.FINISHED) {
            throw new KookminEatException(ErrorCode.DELIVERY_ALREADY_FINISHED);
        }

        // 정상 완료 처리
        delivery.updateStatus(DeliveryStatus.FINISHED);
        delivery.getOrder().setStatus(OrderStatus.FINISHED);

        return delivery;
    }
}