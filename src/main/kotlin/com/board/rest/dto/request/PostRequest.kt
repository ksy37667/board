package com.board.rest.dto.request

import com.board.entity.Post
import javax.validation.constraints.NotBlank


data class PostRequest(
    @NotBlank
    val title: String,

    @NotBlank
    val content: String,

    @NotBlank
    val status: Post.Status
)