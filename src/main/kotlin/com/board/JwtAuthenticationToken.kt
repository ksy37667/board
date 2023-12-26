package com.board

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority


class JwtAuthenticationToken : AbstractAuthenticationToken {
    private var token: String? = null
    private var principal: Any? = null // 로그인한 사용자 id , email
    private var credentials: Any? = null

    /**
     * Creates a token with the supplied array of authorities.
     *
     * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal
     * represented by this authentication object.
     */
    constructor(
        authorities: Collection<GrantedAuthority?>?,
        principal: Any?, credentials: Any?
    ) : super(authorities) {
        this.principal = principal
        this.credentials = credentials
        this.isAuthenticated = true
    }

    fun getToken(): String? {
        return this.token
    }

    constructor(token: String?) : super(null) {
        this.token = token
        this.isAuthenticated = false
    }

    override fun getCredentials(): Any? {
        return credentials
    }

    override fun getPrincipal(): Any? {
        return principal
    } // 기존 코드를 수정
}
