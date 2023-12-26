package com.board.repository

import com.board.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

interface UserRepository: JpaRepository<User, Long> {

    @Transactional(readOnly = true)
    fun existsByUsername(username: String): Boolean

    @Transactional(readOnly = true)
    fun existsByEmail(email: String): Boolean

    @Transactional(readOnly = true)
    fun findByEmail(email: String): User?

}