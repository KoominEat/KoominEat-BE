package com.koomineat.koomineat.domain.delivery.service;

import com.koomineat.koomineat.domain.auth.entity.User;
import com.koomineat.koomineat.domain.auth.service.UserService;
import com.koomineat.koomineat.domain.delivery.dto.response.DeliveryListResponse;
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
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final UserService userService;

    private static final int DELIVERY_TIMEOUT_MINUTES = 3;

    @Override
    @Transactional
    public Delivery createDelivery(Order order, String destination, String message) {
        Delivery delivery = Delivery.builder()
                .order(order)
                .destination(destination)
                .message(message)
                .status(DeliveryStatus.READY)
                .estimatedTime(null)
                .build();
        return deliveryRepository.save(delivery);
    }

    @Override
    @Transactional
    public Delivery acceptDelivery(String authToken, Long deliveryId) {

        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new KookminEatException(ErrorCode.DELIVERY_NOT_FOUND));

        Order order = delivery.getOrder();

        User user = userService.getUserFromAuthToken(authToken);

        // 이미 끝난 배달이면 수락 불가
        if (delivery.getStatus() == DeliveryStatus.FINISHED) {
            throw new KookminEatException(ErrorCode.DELIVERY_ALREADY_FINISHED);
        }

        if (delivery.getStatus() == DeliveryStatus.CANCELED) {
            throw new KookminEatException(ErrorCode.DELIVERY_ALREADY_CANCELED);
        }

        // 이미 누가 잡았으면 거부
        if (delivery.getStatus() == DeliveryStatus.DELIVERING &&
                delivery.getDeliveryUser() != null &&
                !delivery.getDeliveryUser().getId().equals(user.getId())) {

            throw new KookminEatException(ErrorCode.DELIVERY_ALREADY_ACCEPTED);
        }

        // 수락 가능 시간 초과 검사
//        if (order.getCreatedAt().plusMinutes(DELIVERY_TIMEOUT_MINUTES).isBefore(LocalDateTime.now())) {
//            delivery.updateStatus(DeliveryStatus.CANCELED);
//            order.updateOrderType(OrderType.PICKUP);
//            return delivery;
//        }

        // 정상 수락
        delivery.updateDeliveryUser(user);
        delivery.updateStatus(DeliveryStatus.DELIVERING);

        // 일단 바로 finish.
        delivery.finish();
        return delivery;
    }

    @Override
    @Transactional
    public Delivery finishDelivery(Long deliveryId) {

        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new KookminEatException(ErrorCode.DELIVERY_NOT_FOUND));

//        if (delivery.getStatus() == DeliveryStatus.CANCELED)
//            throw new KookminEatException(ErrorCode.DELIVERY_ALREADY_CANCELED);
//
//        if (delivery.getStatus() == DeliveryStatus.FINISHED)
//            throw new KookminEatException(ErrorCode.DELIVERY_ALREADY_FINISHED);

        delivery.finish();

        return delivery;
    }

    // 전달 요청 리스트
    @Override
    public List<DeliveryListResponse> getRequestList(Long locationId) {

        List<Delivery> list;

        if (locationId == null) {
            // 전체 조회
            list = deliveryRepository
                    .findByStatusOrderByOrderCreatedAtAsc(DeliveryStatus.READY);
        } else {
            // 특정 위치(locationId)만 조회
            list = deliveryRepository
                    .findByStatusAndOrderStoreLocationIdOrderByOrderCreatedAtAsc(
                            DeliveryStatus.READY, locationId
                    );
        }

        return list.stream()
                .map(DeliveryListResponse::from)
                .toList();
    }

    // 내가 수락한 전달 목록
    @Override
    public List<DeliveryListResponse> getMyAcceptedList(String authToken) {
        User me = userService.getUserFromAuthToken(authToken);

        return deliveryRepository
                .findByDeliveryUserIdOrderByOrderCreatedAtAsc(me.getId())
                .stream()
                .map(DeliveryListResponse::from)
                .toList();
    }

    // 내가 요청한 배달 정보
    @Override
    public List<DeliveryListResponse> getMyDeliveryRequests(String authToken) {
        User me = userService.getUserFromAuthToken(authToken);

        return deliveryRepository
                .findByOrderUserIdOrderByOrderCreatedAtAsc(me.getId())
                .stream()
                .map(DeliveryListResponse::from)
                .toList();
    }
}