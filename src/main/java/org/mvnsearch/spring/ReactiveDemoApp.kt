package org.mvnsearch.spring

import io.reactivex.Single
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

/**
 * reactive demo app
 *
 * @author linux_china
 */
@SpringBootApplication
class ReactiveDemoApp

@RestController
class PortalController {

    @GetMapping("/")
    fun index(): Mono<String> {
        return "Hello Mono!".toMono()
    }

    @GetMapping("/rx")
    fun rxIndex(): Single<String> {
        return Single.just("Hello Single!")
    }
}

fun main(args: Array<String>) {
    runApplication<ReactiveDemoApp>(*args)
}
