package com.board.service

import com.board.entity.Post
import com.board.entity.enums.UserStatus
import com.board.exception.*
import com.board.repository.CommentRepository
import com.board.repository.PostRepository
import com.board.repository.UserRepository
import com.board.rest.dto.LoginUserDto
import com.board.rest.dto.request.PostRequest
import com.board.rest.dto.response.PostResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import javax.transaction.Transactional


@Service
class PostCrudService(
    private val postRepository: PostRepository
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    fun read(
        id: Long
    ): PostResponse {
        val post = postRepository.findById(id).orElseThrow { throw PostNotFoundException() }

        if (post.status == Post.Status.DELETED) {
            throw PostDeletedException()
        }

        logger.info("$id 조회 => userId = ${post.userId}")
        return PostResponse(post)
    }

    fun create(
        loginUserDto: LoginUserDto,
        request: PostRequest
    ): PostResponse {
        val post = postRepository.save(
            Post(
                title = request.title,
                content = request.content,
                status = request.status,
                view = 0,
                userId = loginUserDto.userId
            )
        )

        return PostResponse(post)
    }

    fun update(
        loginUserDto: LoginUserDto,
        request: PostRequest,
        id: Long
    ): PostResponse {
        val post = postRepository.findById(id).orElseThrow { throw PostNotFoundException() }
        if (loginUserDto.userId != post.userId) throw NotAuthorityException()
        return PostResponse(
            postRepository.save(
                post.apply {
                    this.title = post.title
                    this.content = post.content
                    this.status = post.status
                }
            )
        )
    }

    fun delete(
        loginUserDto: LoginUserDto,
        id: Long
    ): String {
        val post = postRepository.findById(id).orElseThrow { throw PostNotFoundException() }
        if (loginUserDto.userId != post.userId) throw NotAuthorityException()

        post.status = Post.Status.DELETED
        postRepository.save(post)

        return "success"
    }

}