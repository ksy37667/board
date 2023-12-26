package com.board.repository

import com.board.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository: JpaRepository<Comment, Long> {

    @Query(value = """
        SELECT c
        FROM Comment c 
        left join fetch c.post 
        join fetch c.user 
        where c.post.id = :postId
    """)
    fun findCommentsByPostId(postId: Long): MutableList<Comment>

}