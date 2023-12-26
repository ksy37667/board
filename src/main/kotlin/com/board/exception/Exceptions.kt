package com.board.exception

import org.springframework.security.core.AuthenticationException

open class BoardException(
    val code: ErrorCode,
    override var message: String? = code.message
): RuntimeException(
    message ?: code.message
)


class PostNotFoundException : BoardException(ErrorCode.NOT_FOUND_POST)
class PostDeletedException : BoardException(ErrorCode.DELETE_POST)
class NotAuthorityException : BoardException(ErrorCode.NOT_AUTHORITY)
class AlreadyExistUsernameException : BoardException(ErrorCode.USERNAME_ALREADY_EXIST)

class AlreadyExistEmailException : BoardException(ErrorCode.EMAIL_ALREADY_EXIST)
class NotFoundRoleException : BoardException(ErrorCode.NOT_FOUND_ROLE)

class EmailNotFoundException : BoardException(ErrorCode.NOT_FOUND_EMAIL)
class UserInfoIncorrectException : BoardException(ErrorCode.USERINFO_INCORRECT)
class NotFoundUserException : BoardException(ErrorCode.NOT_FOUND_USER)
class DormantUserException : BoardException(ErrorCode.DORMANT_USER)
class NotFoundCommentException : BoardException(ErrorCode.NOT_FOUND_COMMENT)


open class BoardJwtException(
    val code: JwtExceptionCode,
    override var message: String? = code.message
): AuthenticationException(
    message ?: code.message
)


class NotFoundTokenException : BoardJwtException(JwtExceptionCode.NOT_FOUND_TOKEN_USER)