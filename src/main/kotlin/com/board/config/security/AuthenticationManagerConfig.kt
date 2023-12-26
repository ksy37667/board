package com.board.config.security

import com.board.provider.JwtAuthenticationProvider
import com.board.filter.JwtAuthenticationFilter
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
class AuthenticationManagerConfig(
    private val jwtAuthenticationProvider: JwtAuthenticationProvider
): AbstractHttpConfigurer<AuthenticationManagerConfig, HttpSecurity>() {

    @Throws(Exception::class)
    override fun configure(builder: HttpSecurity) {
        val authenticationManager = builder.getSharedObject(
            AuthenticationManager::class.java
        )
        builder.addFilterBefore(
            JwtAuthenticationFilter(authenticationManager),
            UsernamePasswordAuthenticationFilter::class.java
        ).authenticationProvider(jwtAuthenticationProvider)
    }
}


