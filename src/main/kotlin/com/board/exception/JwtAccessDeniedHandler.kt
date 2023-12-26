package com.board.exception

import com.board.JwtAuthenticationToken
import com.board.rest.dto.LoginInfoDto
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class JwtAccessDeniedHandler: AccessDeniedHandler {

    private val logger = LoggerFactory.getLogger(this::class.java)
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        e: AccessDeniedException
    ) {
        val authentication = SecurityContextHolder.getContext().authentication
        val jwtAuthenticationToken = authentication as JwtAuthenticationToken
        val principal = jwtAuthenticationToken.principal
        val loginInfo = principal as LoginInfoDto

        logger.error("${e.message} // email = ${loginInfo.email} // userId = ${loginInfo.userId}")
        response.status = HttpStatus.FORBIDDEN.value()
    }
}