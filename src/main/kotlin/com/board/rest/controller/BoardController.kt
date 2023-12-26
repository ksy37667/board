package com.board.rest.controller

import com.board.aspect.IfLogin
import com.board.rest.controller.support.APIEnvelop
import com.board.rest.controller.support.APIEnvelopPage
import com.board.rest.dto.LoginUserDto
import com.board.rest.dto.request.PostRequest
import com.board.rest.dto.request.PostsSearchRequest
import com.board.service.PostCrudService
import com.board.service.PostSearchService
import com.board.wrapDataResponse
import com.board.wrapPageResponse
import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.*

@RestController
@Api(tags = ["Board API"], description = "게시판 API")
@RequestMapping("/api/board")
class BoardController(
    private val postSearchService: PostSearchService,
    private val postCrudService: PostCrudService
) {

    @GetMapping("/search")
    fun search(
        request: PostsSearchRequest
    ): APIEnvelopPage<*> {
        return postSearchService.search(request).wrapPageResponse()
    }

    @GetMapping("/read/{id}")
    fun read(
       @PathVariable(name = "id") id: Long
    ): APIEnvelop<*> {
        return postCrudService.read(id).wrapDataResponse()
    }

    @DeleteMapping("/delete/{id}")
    fun delete(
        @IfLogin loginUserDto: LoginUserDto,
        @RequestHeader("Authorization") token: String,
        @PathVariable(name = "id") id: Long
    ): APIEnvelop<*> {
        return postCrudService.delete(loginUserDto, id).wrapDataResponse()
    }

    @PostMapping("/create")
    fun create(
        @IfLogin loginUserDto: LoginUserDto,
        @RequestHeader("Authorization") token: String,
        @RequestBody(required = true) request: PostRequest
    ): APIEnvelop<*> {
        return postCrudService.create(loginUserDto, request).wrapDataResponse()
    }

    @PutMapping("/update/{id}")
    fun update(
        @PathVariable id: Long,
        @IfLogin loginUserDto: LoginUserDto,
        @RequestHeader("Authorization") token: String,
        @RequestBody(required = true) request: PostRequest
    ): APIEnvelop<*> {
        return postCrudService.update(loginUserDto, request, id).wrapDataResponse()
    }

}