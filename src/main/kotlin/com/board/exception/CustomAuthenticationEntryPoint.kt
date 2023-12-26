package com.board.exception

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
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        val exception = request?.getAttribute("exception").toString()

        logger.error("Commence Get Exception : {}", exception)
        logger.error("entry point >> not found token")

        when (exception) {
            JwtExceptionCode.INVALID_TOKEN.code -> {
                logger.error("entry point >> invalid token")
            }

            JwtExceptionCode.EXPIRED_TOKEN.code -> {
                logger.error("entry Point >> expired token")
            }

            JwtExceptionCode.UNSUPPORTED_TOKEN.code -> {
                logger.error("entry point >> unsupported token")
            }

            JwtExceptionCode.NOT_FOUND_TOKEN.code -> {
                logger.error("entry point >> not found token")
            }

            else -> {
                logger.error(exception)
            }
        }
    }
}