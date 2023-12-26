package com.board.config.swagger


import org.slf4j.LoggerFactory
import org.springframework.boot.info.BuildProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.http.ResponseEntity
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.*



@Configuration
@EnableSwagger2
class SwaggerConfig(
    private val buildProperties: BuildProperties,
    private val environment: Environment
): WebMvcConfigurer {
    private val logger = LoggerFactory.getLogger(SwaggerConfig::class.java)

    @Bean
    fun messageDocket(): Docket {
        logger.debug("Starting Swagger...")

        return Docket(DocumentationType.SWAGGER_2)
            .enable(true)
            .useDefaultResponseMessages(false)
            .ignoredParameterTypes()
            .apiInfo(apiInfo())
            .genericModelSubstitutes(
                Optional::class.java,
                ResponseEntity::class.java
            )
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.board.rest.controller"))
            .build()
    }

    private fun apiInfo() = ApiInfoBuilder()
        .title(buildProperties.name)
        .version(buildProperties.version)
        .build()
}