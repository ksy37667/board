package com.board.repository

import com.board.entity.Role
import com.board.entity.enums.RoleType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional


interface RoleRepository: JpaRepository<Role, Long> {

    @Transactional(readOnly = true)
    fun findByName(name: RoleType): Role?
}