package com.board.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val message: String
) {
    ERR_INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버와의 통신이 원활하지 않습니다."),
    NOT_FOUND_POST(HttpStatus.NOT_FOUND, "해당 게시글을 찾을 수 없습니다."),
    DELETE_POST(HttpStatus.NOT_FOUND, "삭제된 게시글입니다."),
    NOT_AUTHORITY(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    NICKNAME_ALREADY_EXIST(HttpStatus.CONFLICT, "이미 존재하는 nickname 입니다."),
    EMAIL_ALREADY_EXIST(HttpStatus.CONFLICT, "이미 존재하는 email 입니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),
    NOT_FOUND_ROLE(HttpStatus.BAD_REQUEST, "잘못된 권한 입니다."),
    NOT_FOUND_EMAIL(HttpStatus.BAD_REQUEST, "존재 하지 않는 이메일입니다.."),
    USERINFO_INCORRECT(HttpStatus.UNAUTHORIZED, "아이디 혹은 비밀번호를 다시 입력해주세요."),
    DORMANT_USER(HttpStatus.FORBIDDEN, "휴면 상태 아이디입니다."),
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "Method type is invalid"),
}


enum class JwtExceptionCode(
    val code: String,
    val message: String
) {
    UNKNOWN_ERROR("UNKNOWN_ERROR", "UNKNOWN_ERROR"),
    NOT_FOUND_TOKEN("NOT_FOUND_TOKEN", "Headers에 토큰 형식의 값 찾을 수 없음"),
    ACCESS_DENIED_EXCEPTION("ACCESS_DENIED", "권한이 없습니다."),
    INVALID_TOKEN("INVALID_TOKEN", "유효하지 않은 토큰"),
    EXPIRED_TOKEN("EXPIRED_TOKEN", "기간이 만료된 토큰"),
    UNSUPPORTED_TOKEN("UNSUPPORTED_TOKEN", "지원하지 않는 토큰"),
    NOT_FOUND_TOKEN_USER("NOT_FOUND_TOKEN_USER", "찾을 수 없는 토큰 유저")
}