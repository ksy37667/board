package com.board.rest.controller

import com.board.aspect.IfLogin
import com.board.rest.controller.support.APIEnvelop
import com.board.rest.dto.LoginUserDto
import com.board.rest.dto.request.CommentUpdateRequest
import com.board.rest.dto.request.CommentWriteRequest
import com.board.service.CommentCrudService
import com.board.wrapDataResponse
import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.*


@Api(tags = ["Comment API"], description = "댓글 API")
@RestController("/api/comment")
class CommentController(
    private val commentCrudService: CommentCrudService
) {

    @PostMapping("/write")
    fun write(
        @RequestHeader("Authorization") token: String,
        @IfLogin loginUserDto: LoginUserDto,
        @RequestBody commentWriteRequest: CommentWriteRequest
    ): APIEnvelop<*> {
        return commentCrudService.write(commentWriteRequest, loginUserDto).wrapDataResponse()
    }

    @GetMapping("/read/{postId}")
    fun read(
        @PathVariable(name = "postId") postId: Long
    ): APIEnvelop<*> {
        return commentCrudService.read(postId).wrapDataResponse()
    }

    @DeleteMapping("/delete/{commentId}")
    fun delete(
        @RequestHeader("Authorization") token: String,
        @IfLogin loginUserDto: LoginUserDto,
        @PathVariable(name = "commentId") commentId: Long
    ): APIEnvelop<*> {
        return commentCrudService.delete(commentId, loginUserDto).wrapDataResponse()
    }

    @PutMapping("/update/{commentId}")
    fun update(
        @RequestHeader("Authorization") token: String,
        @IfLogin loginUserDto: LoginUserDto,
        @RequestBody commentUpdateRequest: CommentUpdateRequest
    ): APIEnvelop<*> {
        return commentCrudService.update(commentUpdateRequest, loginUserDto).wrapDataResponse()
    }

}