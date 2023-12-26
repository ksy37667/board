package com.board.config.security

import com.board.exception.CustomAuthenticationEntryPoint
import com.board.exception.JwtAccessDeniedHandler
import io.swagger.models.HttpMethod
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher


@Configuration
@EnableWebSecurity
class WebSecurityConfig(
    private val authenticationManagerConfig: AuthenticationManagerConfig,
    private val customAuthenticationEntryPoint: CustomAuthenticationEntryPoint,
    private val accessDeniedHandler: JwtAccessDeniedHandler
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .formLogin().disable()
            .csrf().disable()
            .cors()
            .and()
            .apply(authenticationManagerConfig)
            .and()
            .httpBasic().disable()
            .authorizeRequests()
            .requestMatchers(
                // 게시글 검색, 읽기
                AntPathRequestMatcher( "/api/board/search"),
                AntPathRequestMatcher( "/api/board/read"),

                AntPathRequestMatcher("/api/comment/read"),

                // 로그인, 회원가입, 재발급
                AntPathRequestMatcher("/api/user/login"),
                AntPathRequestMatcher("/api/user/refreshToken"),
                AntPathRequestMatcher("/api/user/sign-up"),

                // 스웨거
                AntPathRequestMatcher( "/swagger-ui.html"),
                AntPathRequestMatcher( "/swagger/**"),

            ).permitAll()
            .requestMatchers(
                // 게시글
                AntPathRequestMatcher( "/api/board/create", "POST"),
                // 댓글
                AntPathRequestMatcher("/api/comment/write", "POST"),
                AntPathRequestMatcher("/api/comment/delete", "DELETE"),
                AntPathRequestMatcher("/api/comment/update", "PUT"),
                AntPathRequestMatcher("/hello")
            ).hasRole("USER")
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(customAuthenticationEntryPoint)
            .accessDeniedHandler(accessDeniedHandler)

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}