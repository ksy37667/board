package com.board.rest.dto.response

import com.board.entity.Post
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.ZonedDateTime

data class PostResponse (
    val id : Long,
    val title: String,
    val content: String,
    val view: Long,
    val status: Post.Status,
    val userId: Long,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZZZZZ", timezone = "Asia/Seoul")
    val createdAt: ZonedDateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZZZZZ", timezone = "Asia/Seoul")
    val modifiedAt: ZonedDateTime
) {
    constructor(
        post: Post
    ): this(
        id = post.id,
        title = post.title,
        content = post.content,
        view = post.view,
        status = post.status,
        userId = post.userId,
        createdAt = post.createdAt,
        modifiedAt = post.createdAt
    )
}