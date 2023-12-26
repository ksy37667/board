package com.board.service

import com.board.JwtTokenizer
import com.board.entity.RefreshToken
import com.board.entity.enums.UserStatus
import com.board.exception.DormantUserException
import com.board.exception.UserInfoIncorrectException
import com.board.repository.RefreshTokenRepository
import com.board.repository.RoleRepository
import com.board.repository.UserRepository
import com.board.rest.dto.request.LoginRequest
import com.board.rest.dto.response.LoginResponse
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class LoginService(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtTokenizer: JwtTokenizer
) {
    private val logger = LoggerFactory.getLogger(this::class.java)
    @Transactional
    fun login(
        req: LoginRequest
    ): LoginResponse {
        val reqEmail = req.email
        val reqPassword = req.password

        val user = userRepository.findByEmail(reqEmail) ?: throw UserInfoIncorrectException()
        val role = roleRepository.findById(user.roleId).get()

        if(!passwordEncoder.matches(reqPassword, user.password) || user.status == UserStatus.WITHDRAWN) {
            throw UserInfoIncorrectException()
        }

        if (user.status == UserStatus.DORMANT) {
            throw DormantUserException()
        }

        val accessToken = jwtTokenizer.createAccessToken(user.id, reqEmail, user.username, role.name.toString())!!
        val refreshToken = jwtTokenizer.createRefreshToken(user.id, reqEmail, user.username, role.name.toString())!!

        val refreshTokenEntity = refreshTokenRepository.findByUserId(user.id)

        if (refreshTokenEntity != null) {
            refreshTokenEntity.value = refreshToken
            refreshTokenRepository.save(refreshTokenEntity)
        } else {
            refreshTokenRepository.save(
                RefreshToken(
                    value = refreshToken,
                    userId = user.id
                )
            )
        }

        return LoginResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
            userId = user.id,
            username = user.username
        )

    }
}