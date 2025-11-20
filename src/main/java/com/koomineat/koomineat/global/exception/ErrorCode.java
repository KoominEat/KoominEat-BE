package com.koomineat.koomineat.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // ===== store =====
    STORE_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "S001", "카테고리를 찾을 수 없습니다."),
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "S002", "가게를 찾을 수 없습니다."),

    // ===== menu =====
    MENUITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "M001", "메뉴를 찾을 수 없습니다."),

    // ===== global =====
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "G001", "잘못된 입력 값입니다."),

    // ===== user/auth =====
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "사용자를 찾을 수 없습니다."),
    INVALID_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, "U002", "유효하지 않은 인증 토큰입니다."),

    // ===== order =====
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "O001", "주문을 찾을 수 없습니다."),

    // ===== delivery =====
    DELIVERY_NOT_FOUND(HttpStatus.NOT_FOUND, "D001", "전달 요청을 찾을 수 없습니다."),
    DELIVERY_ALREADY_ACCEPTED(HttpStatus.BAD_REQUEST, "D002", "이미 다른 전달자가 수락한 요청입니다."),
    DELIVERY_TIMEOUT(HttpStatus.BAD_REQUEST, "D003", "전달 수락 가능 시간이 초과되었습니다."),
    DELIVERY_ALREADY_FINISHED(HttpStatus.BAD_REQUEST, "D004", "이미 완료된 전달 요청입니다."),
    DELIVERY_ALREADY_CANCELED(HttpStatus.BAD_REQUEST, "D005", "이미 취소된 전달 요청입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}