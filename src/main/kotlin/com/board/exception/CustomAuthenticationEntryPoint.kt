package com.board.exception

import com.google.gson.Gson
import org.slf4j.LoggerFactory
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class CustomAuthenticationEntryPoint: AuthenticationEntryPoint {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Throws(IOException::class, ServletException::class)
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        val exception = request.getAttribute("exception").toString()

        logger.error("Commence Get Exception : {}", exception)
        logger.error("entry point >> not found token")

        when (exception) {
            JwtExceptionCode.INVALID_TOKEN.code -> {
                logger.error("entry point >> invalid token")
                setResponse(response, JwtExceptionCode.INVALID_TOKEN)
            }

            JwtExceptionCode.EXPIRED_TOKEN.code -> {
                logger.error("entry Point >> expired token")
                setResponse(response, JwtExceptionCode.EXPIRED_TOKEN)
            }

            JwtExceptionCode.UNSUPPORTED_TOKEN.code -> {
                logger.error("entry point >> unsupported token")
                setResponse(response, JwtExceptionCode.UNSUPPORTED_TOKEN)
            }

            JwtExceptionCode.NOT_FOUND_TOKEN.code -> {
                logger.error("entry point >> not found token")
                setResponse(response, JwtExceptionCode.NOT_FOUND_TOKEN)
            }

            else -> {
                setResponse(response, JwtExceptionCode.UNKNOWN_ERROR)
            }
        }
    }

    @Throws(IOException::class)
    private fun setResponse(response: HttpServletResponse, exceptionCode: JwtExceptionCode) {
        response.contentType = "application/json;charset=UTF-8"
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        val errorInfo = HashMap<String, Any>()
        errorInfo["message"] = exceptionCode.message
        errorInfo["code"] = exceptionCode.code
        val gson = Gson()
        val responseJson = gson.toJson(errorInfo)
        response.writer.print(responseJson)
    }
}