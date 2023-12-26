package com.board.aspect

import com.board.JwtAuthenticationToken
import com.board.entity.enums.RoleType
import com.board.rest.dto.LoginInfoDto
import com.board.rest.dto.LoginUserDto
import org.slf4j.LoggerFactory
import org.springframework.core.MethodParameter
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer


class IfLoginAspect: HandlerMethodArgumentResolver {

    private val logger = LoggerFactory.getLogger(this::class.java)
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.getParameterAnnotation(IfLogin::class.java) != null
                && parameter.parameterType == LoginUserDto::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        logger.info("{}",parameter.getParameterAnnotation(IfLogin::class.java))
        val authentication: Authentication = try {
            SecurityContextHolder.getContext().authentication
        } catch (ex: Exception) {
            return null
        } ?: return null

        val jwtAuthenticationToken = authentication as JwtAuthenticationToken

        val principal = jwtAuthenticationToken.principal // LoginInfoDto
        val loginInfoDto = principal as LoginInfoDto
        val authorities = authentication.getAuthorities()
        val role = authorities.first().authority

        logger.info("{}", loginInfoDto)

        return LoginUserDto(
            email = loginInfoDto.email,
            userId = loginInfoDto.userId,
            name = loginInfoDto.name,
            role = RoleType.of(role)
        )
    }

}