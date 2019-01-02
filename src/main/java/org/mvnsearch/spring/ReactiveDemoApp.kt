package org.mvnsearch.spring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

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
    fun index(): String {
        return "Hello World!"
    }
}

fun main(args: Array<String>) {
    runApplication<ReactiveDemoApp>(*args)
}
