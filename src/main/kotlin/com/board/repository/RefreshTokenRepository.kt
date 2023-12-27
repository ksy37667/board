package com.board.repository

import com.board.entity.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface RefreshTokenRepository: JpaRepository<RefreshToken, Long>{

    @Transactional(readOnly = true)
    fun findByUserId(userId: Long): RefreshToken?

    @Transactional(readOnly = true)
    fun findByValue(value: String): RefreshToken?

    fun deleteByValue(value: String)
}