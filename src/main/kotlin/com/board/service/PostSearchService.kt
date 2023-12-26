package com.board.service

import com.board.repository.PostRepository
import com.board.rest.dto.PostsSearchSpec
import com.board.rest.dto.request.PostsSearchRequest
import com.board.rest.dto.response.PostResponse
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

@Service
class PostSearchService(
    private val postRepository: PostRepository
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun search(
        request: PostsSearchRequest
    ): Page<PostResponse> {
        val res = postRepository.findAll(
            PostsSearchSpec.searchWith(request.convertSearchKeyMap()), request.toPageRequest()
        )

        return res.map { PostResponse(it) }
    }
}