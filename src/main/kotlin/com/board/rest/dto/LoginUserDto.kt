package com.board.rest.dto

import com.board.entity.enums.RoleType
import io.swagger.annotations.ApiModelProperty

data class LoginUserDto(
    @ApiModelProperty(hidden = true)
    val email: String,

    @ApiModelProperty(hidden = true)
    val name: String,

    @ApiModelProperty(hidden = true)
    val userId: Long,

    @ApiModelProperty(hidden = true)
    val role: RoleType?
)