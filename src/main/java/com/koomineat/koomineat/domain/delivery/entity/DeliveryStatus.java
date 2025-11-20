package com.koomineat.koomineat.domain.delivery.entity;

public enum DeliveryStatus {
    READY,          // 주문 생성 직후(수락 대기)
    DELIVERING,     // 전달자가 수락함
    FINISHED,       // 전달 완료
    CANCELED        // 수락 시간 초과 -> 자동 PICKUP 전환
}