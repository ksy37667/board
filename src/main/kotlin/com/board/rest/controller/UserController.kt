package com.board.rest.controller

import com.board.rest.controller.support.APIEnvelop
import com.board.rest.controller.support.RestSupport
import com.board.rest.dto.request.LoginRequest
import com.board.rest.dto.request.RefreshTokenRequest
import com.board.rest.dto.request.SignupRequest
import com.board.service.LoginService
import com.board.service.SignUpService
import com.board.service.RefreshTokenService
import com.board.wrapDataResponse
import io.swagger.annotations.Api
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid



@Api(tags = ["User API"], description = "유저 API")
@RestController("/api/user")
class UserController(
    private val signUpService: SignUpService,
    private val loginService: LoginService,
    private val refreshTokenService: RefreshTokenService
): RestSupport() {
    @PostMapping("/sigh-up")
    fun sighUp(
        @RequestBody @Valid request: SignupRequest
    ): APIEnvelop<*> {
        return signUpService.signUp(request).wrapDataResponse()
    }

    @PostMapping("/login")
    fun login(
        @RequestBody @Valid request: LoginRequest
    ): APIEnvelop<*> {
        return loginService.login(request).wrapDataResponse()
    }

    @DeleteMapping("/logout")
    fun logout(
        @RequestBody request: RefreshTokenRequest
    ) : APIEnvelop<ResponseEntity<*>> {
        refreshTokenService.deleteRefreshToken(request.refreshToken)
        return response("Logout Success!").wrapDataResponse()
    }

    @PostMapping("/refreshToken")
    fun requestRefresh(
        @RequestBody request: RefreshTokenRequest
    ): APIEnvelop<*> {
        return refreshTokenService.refresh(request).wrapDataResponse()
    }

}