package com.board

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.Key
import java.util.*


@Component
class JwtTokenizer(
    @Value("\${board.jwt.secretKey}") accessSecret: String,
    @Value("\${board.jwt.refreshKey}") refreshSecret: String
) {
    private var accessSecret = accessSecret.toByteArray(StandardCharsets.UTF_8)
    private var refreshSecret = refreshSecret.toByteArray(StandardCharsets.UTF_8)

    private val logger = LoggerFactory.getLogger(this::class.java)

    companion object {
        const val ACCESS_TOKEN_EXPIRE_COUNT = 200 * 60 * 1000L // 200 minutes
        const val REFRESH_TOKEN_EXPIRE_COUNT = 7 * 24 * 60 * 60 * 1000L // 7 days
    }

    fun createAccessToken(id: Long, email: String, name: String, role: String): String? {
        logger.info("$accessSecret")
        return createToken(id, email, name, role, ACCESS_TOKEN_EXPIRE_COUNT, accessSecret)
    }

    fun createRefreshToken(id: Long, email: String, name: String, role: String): String? {
        logger.info("$refreshSecret")
        return createToken(id, email, name, role, REFRESH_TOKEN_EXPIRE_COUNT, refreshSecret)
    }


    private fun createToken(
        id: Long, email: String, name: String, role: String,
        expire: Long, secretKey: ByteArray
    ): String {
        val claims = Jwts.claims().setSubject(email)

        claims["roles"] = role
        claims["userId"] = id
        claims["name"] = name

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(Date())
            .setExpiration(Date(Date().time + expire))
            .signWith(getSigningKey(secretKey))
            .compact()
    }

    fun getUserIdFromToken(token: String): Long? {
        var token = token
        val tokenArr = token.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        token = tokenArr[1]
        val claims = parseToken(token, accessSecret)
        return claims["memberId"] as Long?
    }

    fun parseAccessToken(accessToken: String?): Claims {
        return parseToken(accessToken, accessSecret)
    }

    fun parseRefreshToken(refreshToken: String?): Claims {
        return parseToken(refreshToken, refreshSecret)
    }

    fun parseToken(token: String?, secretKey: ByteArray?): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey(secretKey))
            .build()
            .parseClaimsJws(token)
            .body
    }

    fun getSigningKey(secretKey: ByteArray?): Key {
        return Keys.hmacShaKeyFor(secretKey)
    }
}