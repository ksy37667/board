package com.board.rest.dto.response

data class LoginResponse(
    val accessToken: String,

    val refreshToken: String,

    val userId: Long,

    val username: String
)