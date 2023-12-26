package com.board.rest.dto.request

import javax.validation.constraints.NotBlank

data class CommentUpdateRequest(
    @NotBlank
    val commentId: Long,

    @NotBlank
    val content: String
)