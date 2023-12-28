package com.board.entity.view

import java.time.ZonedDateTime

interface CommentInfo {
    val id: Long
    val postId: Long
    val userId: Long
    val nickname: String
    val content: String
    val createdAt: ZonedDateTime
    val modifiedAt: ZonedDateTime
}