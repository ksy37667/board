package com.board.advice

import com.board.exception.BoardException
import com.board.exception.ErrorCode
import com.board.exception.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalControllerAdvice {
    private val log = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(BoardException::class)
    fun handleBoardException(e: BoardException): ResponseEntity<*> {
        log.error("BoardException / ${e.message}")
        val response = ErrorResponse(e.code, e)

        return ResponseEntity(response, e.code.status)
    }

    /*
    * 정의되지 않은 모든 예외를 INTERNAL_SERVER_ERROR 로 처리한다.
    **/
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        log.error("handleException", e)
        val response = ErrorResponse(ErrorCode.ERR_INTERNAL_ERROR)
        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    /**
     * 유효성(validation) 검사 실패 exception handler
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<*> {
        log.error("MethodArgumentNotValidException", e)
        val result = e.bindingResult
        val errorMsgList = result.fieldErrors.map { error ->
            error.field + ": " + error.defaultMessage
        }

        return ResponseEntity(errorMsgList, HttpStatus.BAD_REQUEST)
    }
}