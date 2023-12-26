package com.board.provider

import com.board.JwtAuthenticationToken
import com.board.JwtTokenizer
import com.board.rest.dto.LoginInfoDto
import io.jsonwebtoken.Claims
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component


@Component
class JwtAuthenticationProvider(
    private val jwtTokenizer: JwtTokenizer
) : AuthenticationProvider {

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication {
        val authenticationToken = authentication as JwtAuthenticationToken

        // 토큰을 검증한다. 기간이 만료되었는지, 토큰 문자열이 문제가 있는지 등 Exception이 발생한다.
        val claims: Claims = jwtTokenizer.parseAccessToken(authenticationToken.getToken())

        // sub에 암호화된 데이터를 집어넣고, 복호화하는 코드를 넣어줄 수 있다.
        val email = claims.subject
        val name = claims.get("name", String::class.java)
        val userId = claims.get("userId", Integer::class.java).toLong()
        val authorities = getGrantedAuthorities(claims)
        val loginInfo = LoginInfoDto(email = email, userId = userId, name = name)

        return JwtAuthenticationToken(authorities, loginInfo, null)
    }

    private fun getGrantedAuthorities(claims: Claims): List<GrantedAuthority?> {
        val roles = mutableListOf<String>()
        roles.add(claims["roles"] as String)

        val authorities: MutableList<GrantedAuthority?> = ArrayList()
        for (role in roles) {
            authorities.add(GrantedAuthority { role })
        }
        return authorities
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return JwtAuthenticationToken::class.java.isAssignableFrom(authentication)
    }
}