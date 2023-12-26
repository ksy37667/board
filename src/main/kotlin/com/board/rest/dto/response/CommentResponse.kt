package com.board.rest.dto.response

import com.board.entity.Comment
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.ZonedDateTime

data class CommentResponse(
    val id: Long,

    val postId: Long,

    val userId: Long,

    val content: String,

    val nickname: String,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZZZZZ", timezone = "Asia/Seoul")
    val createdAt: ZonedDateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZZZZZ", timezone = "Asia/Seoul")
    val modifiedAt: ZonedDateTime
) {
    companion object {
        fun of(
            comment: Comment
        ): CommentResponse {
            return CommentResponse(
                id = comment.id,
                postId = comment.post.id,
                content = comment.content,
                userId = comment.user.id,
                nickname = comment.user.nickname,
                createdAt = comment.createdAt,
                modifiedAt = comment.modifiedAt
            )

        }
    }
}