package com.example.capstone.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ResponseCode {
    // 400 Bad Request
    BAD_REQUEST(HttpStatus.BAD_REQUEST,  "잘못된 요청입니다.", 1001),
    DIARY_PARAM_NOT_ALLOWED_FORMAT(HttpStatus.BAD_REQUEST,"날짜 형식을 제대로 입력해주세요",1006),
    DIARY_REQUEST_NOT_ALLOWED_FORMAT(HttpStatus.BAD_REQUEST,"",1007),
    // 403 Forbidden
    FORBIDDEN(HttpStatus.FORBIDDEN,  "권한이 없습니다.", 1002),

    // 404 Not Found
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "사용자를 찾을 수 없습니다.", 1003),

    // 405 Method Not Allowed
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 메소드입니다.", 1004),

    // 500 Internal Server Error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,  "서버에 오류가 발생하였습니다.", 1005),

    // 201 Created
    USER_CREATE_SUCCESS(HttpStatus.CREATED,  "사용자 생성 성공", 2001),
    DIARY_CREATE_SUCCESS(HttpStatus.CREATED, "일지 생성 성공", 2002),

    // 200 OK
    DIARY_UPDATE_SUCCESS(HttpStatus.OK, "일지 수정 성공", 2003),
    DIARY_FIND_BY_DATE_SUCCESS(HttpStatus.OK,"일지 조회 성공", 2004),



    // 204 No Content
    DIARY_DELETE_SUCCESS(HttpStatus.NO_CONTENT, "일지 삭제 성공", 2005);

    private final HttpStatus httpStatus;
    private final String message;
    private final Integer code;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
