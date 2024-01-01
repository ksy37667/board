package com.board.service

import com.board.entity.User
import com.board.entity.enums.RoleType
import com.board.entity.enums.SocialType
import com.board.entity.enums.UserStatus
import com.board.exception.AlreadyExistEmailException
import com.board.exception.AlreadyExisNicknameException
import com.board.exception.NotFoundRoleException
import com.board.repository.UserRepository
import com.board.repository.RoleRepository
import com.board.rest.dto.request.SignupRequest
import com.board.rest.dto.response.SignUprResponse
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class SignUpService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val roleRepository: RoleRepository
) {
    private val logger = LoggerFactory.getLogger(this::class.java)
    @Transactional
    fun signUp(
        request: SignupRequest
    ): SignUprResponse {

        if (userRepository.existsByEmail(request.email)) {
            throw AlreadyExistEmailException()
        }

        if (userRepository.existsByNickname(request.nickname)) {
            throw AlreadyExisNicknameException()
        }

        val role = roleRepository.findByName(RoleType.ROLE_USER) ?: throw NotFoundRoleException()

        return SignUprResponse.of(
            userRepository.save(
                User(
                    email = request.email,
                    nickname = request.nickname,
                    username = request.username,
                    status = UserStatus.ACTIVATED,
                    password = passwordEncoder.encode(request.password),
                    socialType = SocialType.NORMAL,
                    roleId = role.id
                )
            )
        )
    }
}