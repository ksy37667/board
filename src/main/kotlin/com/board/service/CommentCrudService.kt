package com.board.service

import com.board.entity.Comment
import com.board.entity.Post
import com.board.exception.NotFoundCommentException
import com.board.exception.NotFoundUserException
import com.board.exception.PostDeletedException
import com.board.exception.PostNotFoundException
import com.board.repository.CommentRepository
import com.board.repository.PostRepository
import com.board.repository.UserRepository
import com.board.rest.dto.LoginUserDto
import com.board.rest.dto.request.CommentUpdateRequest
import com.board.rest.dto.request.CommentWriteRequest
import com.board.rest.dto.response.CommentDeleteResponse
import com.board.rest.dto.response.CommentResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentCrudService(
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
) {

    @Transactional
    fun write(request: CommentWriteRequest, loginUserDto: LoginUserDto): CommentResponse {
        val post = verifyPost(request.postId)
        val user = userRepository.findById(loginUserDto.userId).orElseThrow { throw NotFoundUserException() }
        val comment = commentRepository.save(
                Comment(
                    post = post,
                    content = request.content,
                    user = user
                )
            )

        return CommentResponse.of(comment)
    }

    @Transactional
    fun update(request: CommentUpdateRequest, loginUserDto: LoginUserDto): CommentResponse {
        val comment = commentRepository.findById(request.commentId).orElseThrow { throw NotFoundCommentException() }
        val entity = commentRepository.save(
            comment.apply {
                this.content = request.content
            }
        )
        return CommentResponse.of(entity)
    }

    fun read(postId: Long): List<CommentResponse> {
        val comments = commentRepository.findCommentsByPostId(postId)
        return comments.map { CommentResponse.of(it) }
    }

    @Transactional
    fun delete(commentId: Long, loginUserDto: LoginUserDto): CommentDeleteResponse {
        val comment = commentRepository.findById(commentId).orElseThrow { throw NotFoundCommentException() }
        commentRepository.delete(comment)

        return CommentDeleteResponse(commentId, true)
    }

    private fun verifyPost(postId: Long): Post {
        val post = postRepository.findById(postId).orElseThrow { throw PostNotFoundException() }
        if (post.status == Post.Status.DELETED) { throw PostDeletedException() }

        return post
    }
}