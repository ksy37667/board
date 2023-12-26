package com.board.rest.dto.request

import javax.validation.constraints.NotBlank

data class RefreshTokenRequest(

    @NotBlank
    val refreshToken:String
)