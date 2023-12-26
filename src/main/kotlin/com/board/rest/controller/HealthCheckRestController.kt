package com.board.rest.controller

import com.board.JwtTokenizer
import com.board.rest.controller.support.RestSupport
import com.board.rest.dto.response.HealthCheckResponse
import org.slf4j.LoggerFactory
import org.springframework.boot.info.BuildProperties
import org.springframework.core.env.Environment
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckRestController(
    private val jwtTokenizer: JwtTokenizer,
    private val buildProperties: BuildProperties,
    private val environment: Environment
) : RestSupport() {
    private val logger = LoggerFactory.getLogger(this::class.java)

        @GetMapping("/health")
    fun healthCheck(): ResponseEntity<*> {
        return response(
                HealthCheckResponse(
                    application = buildProperties.name,
                    profiles = environment.activeProfiles.toList(),
                    health = "OK"
                )
        )
    }

    @GetMapping("/hello")
    fun hello(@RequestHeader("Authorization") token: String): String {
        logger.info("hello log token = {}", token)
        return "token = " + jwtTokenizer.getUserIdFromToken(token)
    }

}