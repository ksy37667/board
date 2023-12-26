package com.board.rest.dto

data class LoginInfoDto (
    val userId: Long,
    val email: String,
    val name: String
)