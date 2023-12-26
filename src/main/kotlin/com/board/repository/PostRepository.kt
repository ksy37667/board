package com.board.repository

import com.board.entity.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface PostRepository: JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {

}