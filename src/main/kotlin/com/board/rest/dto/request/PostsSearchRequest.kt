package com.board.rest.dto.request

import com.board.entity.Post
import com.board.rest.controller.support.MultipleSortablePageRequest
import com.board.rest.dto.PostsSearchSpec
import org.springframework.data.domain.Sort
import org.springframework.format.annotation.DateTimeFormat
import java.time.ZonedDateTime
import java.util.*
import javax.validation.constraints.Min


data class PostsSearchRequest(
    override val page: Int = 0,
    override val size: Int = 10,
    override val sort: String?,

    @field:Min(value = 2, message = "두글자부터 검색이 가능합니다.")
    val title: String?,

    @field:Min(value = 1, message = "두글자부터 검색이 가능합니다.")
    val content: String?,
    val userId: Long?,

    @field:Min(value = 1, message = "두글자부터 검색이 가능합니다.")
    val writer: String?,
    val status: MutableList<Post.Status>?,

    @field:DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZZZZZ")
    val fromCreatedAt: ZonedDateTime?,
    @field:DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZZZZZ")
    val toCreatedAt: ZonedDateTime?,
): MultipleSortablePageRequest {

    fun convertSearchKeyMap(): Map<PostsSearchSpec.SearchKey, Any> {
        val searchKeyPosts: MutableMap<PostsSearchSpec.SearchKey, Any> = EnumMap(
            PostsSearchSpec.SearchKey::class.java
        )

        if (status?.isNotEmpty() != null) {
            status.remove(Post.Status.DELETED)
            searchKeyPosts[PostsSearchSpec.SearchKey.STATUS] = status
        } else {
            searchKeyPosts[PostsSearchSpec.SearchKey.STATUS] = listOf(Post.Status.NORMAL, Post.Status.NOTICED)
        }

        if (title != null) searchKeyPosts[PostsSearchSpec.SearchKey.TITLE] = title
        if (content != null) searchKeyPosts[PostsSearchSpec.SearchKey.CONTENT] = content
        if (userId != null) searchKeyPosts[PostsSearchSpec.SearchKey.USERID] = userId
        if (writer != null) searchKeyPosts[PostsSearchSpec.SearchKey.WRITER] = writer
        if (fromCreatedAt != null) searchKeyPosts[PostsSearchSpec.SearchKey.FROM_CREATED_AT] = fromCreatedAt
        if (toCreatedAt != null) searchKeyPosts[PostsSearchSpec.SearchKey.TO_CREATED_AT] = toCreatedAt

        return searchKeyPosts
    }

    override fun convertToSort(requestedSortField: String, direction: Sort.Direction) = Sort.by(Sort.Order.by(requestedSortField).with(direction))
    override fun default() = Sort.by(Sort.Order.desc("createdAt"))
}