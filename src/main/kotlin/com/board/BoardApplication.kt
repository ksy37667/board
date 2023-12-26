package com.board

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.info.BuildProperties
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationListener
import org.springframework.core.env.Environment
import java.util.*

@SpringBootApplication
class BoardApplication(
    private val buildProperties: BuildProperties,
    private val environment: Environment
) : ApplicationListener<ApplicationReadyEvent> {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        logger.info("{} applicationReady, profiles = {}", buildProperties.name, environment.activeProfiles.contentToString())
    }
}

fun main(args: Array<String>) {
    init()
    runApplication<BoardApplication>(*args)
}

fun init() {
    // Setting the Default TimeZone
    TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))

    // parallel set limit
    System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "100")

    /**
     * Setting the JVM TTL for DNS Name Lookups
     * @see <a href="https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/java-dg-jvm-ttl.html">AWS Guide</a>
     */
    java.security.Security.setProperty("networkaddress.cache.ttl", "60")
    java.security.Security.setProperty("networkaddress.cache.negative.ttl", "10")
}
