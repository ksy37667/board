package com.board.exception

class ErrorResponse private constructor(
    var errorCode: String,
    var reason: String,
    var errors: List<FieldError> = listOf()
){

    constructor(code: ErrorCode) : this(reason = code.message, errorCode = code.name)

    constructor(code: ErrorCode, message: String) : this(reason = message, errorCode = code.name)

    constructor(code: ErrorCode, e: BoardException) : this(code, e.message ?: code.message) {
        errors = FieldError.ofEmptyList()
    }
}



class FieldError private constructor(
    val field: String,
    val value: String,
    val reason: String
) {
    companion object {
        fun ofEmptyList(): List<FieldError> {
            return emptyList()
        }
    }
}