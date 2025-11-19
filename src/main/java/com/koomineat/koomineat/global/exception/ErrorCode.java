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
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST,"G001","잘못된 입력 값입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}