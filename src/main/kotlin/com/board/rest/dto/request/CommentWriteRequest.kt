package com.board.rest.dto.request

import javax.validation.constraints.NotBlank

data class CommentWriteRequest (
    @NotBlank
    val postId: Long,

    @NotBlank
    val content: String,
)


