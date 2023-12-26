package com.board.service

import com.board.JwtTokenizer
import com.board.entity.RefreshToken
import com.board.exception.NotFoundTokenException
import com.board.exception.NotFoundUserException
import com.board.repository.RefreshTokenRepository
import com.board.repository.UserRepository
import com.board.rest.dto.request.RefreshTokenRequest
import com.board.rest.dto.response.LoginResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RefreshTokenService(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtTokenizer: JwtTokenizer,
    private val userRepository: UserRepository
) {

    @Transactional
    fun addRefreshToken(refreshToken: RefreshToken): RefreshToken {
        return refreshTokenRepository.save(refreshToken)
    }

    @Transactional
    fun deleteRefreshToken(refreshToken: String) {
        refreshTokenRepository.findByValue(refreshToken)?.let(refreshTokenRepository::delete)
    }

    fun refresh(request: RefreshTokenRequest): LoginResponse {
        val refreshToken = refreshTokenRepository.findByValue(request.refreshToken) ?: throw NotFoundTokenException()

        val claims = jwtTokenizer.parseRefreshToken(refreshToken.value)
        val userId = (claims["userId"] as Int?)!!.toLong()

        val user = userRepository.findById(userId).orElseThrow { throw NotFoundUserException() }
        val role = claims["role"].toString()
        val email = claims.subject

        val accessToken = jwtTokenizer.createAccessToken(userId, email, user.username, role)
        return LoginResponse(
            accessToken = accessToken!!,
            refreshToken = refreshToken.value,
            userId = userId,
            username = user.username
        )
    }
}