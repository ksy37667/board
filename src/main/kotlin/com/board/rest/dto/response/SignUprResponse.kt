package com.board.rest.dto.response

import com.board.entity.User


data class SignUprResponse (

    val id: Long,

    val username: String,

    val nickname: String,

    val email: String,

) {
    companion object {
        fun of(entity: User): SignUprResponse {
            return SignUprResponse(
                id = entity.id,
                username = entity.username,
                nickname = entity.nickname,
                email = entity.email
            )
        }
    }
}