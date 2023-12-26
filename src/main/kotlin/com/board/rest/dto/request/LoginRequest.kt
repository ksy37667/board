package com.board.rest.dto.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class LoginRequest(
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
    val email: String,

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$")
    val password: String
)
