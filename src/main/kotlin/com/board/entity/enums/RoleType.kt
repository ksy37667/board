package com.board.entity.enums

enum class RoleType {
    ROLE_GUEST,
    ROLE_ADMIN,
    ROLE_USER
    ;
    companion object {
        fun of(roleName: String): RoleType? {
            return values().find { it.toString() == roleName }
        }
    }
}

