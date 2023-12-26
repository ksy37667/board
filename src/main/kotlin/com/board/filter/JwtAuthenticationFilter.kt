package com.board.filter

import com.board.JwtAuthenticationToken
import com.board.exception.JwtExceptionCode
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.math.log


class JwtAuthenticationFilter(
    private val authenticationManager: AuthenticationManager
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        var token: String? = ""

        try {
            token = getToken(request)
            if (StringUtils.hasText(token)) {
                getAuthentication(token!!)
            }
            filterChain.doFilter(request, response)
        } catch (e: SecurityException) {
            logger.error("Invalid Token // token = {$token}")
            logger.error("Exception Code ${JwtExceptionCode.INVALID_TOKEN.code}")
            throw BadCredentialsException("not found token exception")
        } catch (e: MalformedJwtException) {
            logger.error("Invalid Token // token = {$token}")
            logger.error("Exception Code ${JwtExceptionCode.INVALID_TOKEN.code}")
            throw BadCredentialsException("not found token exception")
        } catch (e: ExpiredJwtException) {
            logger.error("EXPIRED Token // token = {$token}")
            logger.error("Exception Code ${JwtExceptionCode.EXPIRED_TOKEN.code}")
            throw BadCredentialsException("expired token exception")
        } catch (e: UnsupportedJwtException) {
            logger.error("Unsupported Token // token = {$token}")
            logger.error("Exception Code ${JwtExceptionCode.UNSUPPORTED_TOKEN.code}")
            throw BadCredentialsException("unsupported exception")
        } catch (e: AccessDeniedException) {
            logger.error("Unsupported Token // token = {$token}")
            logger.error("Exception Code ${JwtExceptionCode.ACCESS_DENIED_EXCEPTION.code}")
        } catch (e: Exception) {
            logger.error("JwtFilter - doFilterInternal 오류 발생")
            logger.error("token = {$token}")
            logger.error("Exception message = ${e.message}")
            throw BadCredentialsException("throw new exception!!")
        }
    }

    private fun getAuthentication(token: String) {
        val authenticationToken = JwtAuthenticationToken(token)
        val authenticate = authenticationManager.authenticate(authenticationToken)

        SecurityContextHolder.getContext().authentication = authenticate // 현재 요청에서 언제든지 인증정보를 꺼낼 수 있도록 해준다.
    }

    private fun getToken(request: HttpServletRequest): String? {
        val authorization = request.getHeader("Authorization")
        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer")) {
            val arr = authorization.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            return arr[1]
        }
        return null
    }
}