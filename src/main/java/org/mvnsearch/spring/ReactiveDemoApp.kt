package org.mvnsearch.spring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * reactive demo app
 *
 * @author linux_china
 */
@SpringBootApplication
class ReactiveDemoApp


fun main(args: Array<String>) {
    runApplication<ReactiveDemoApp>(*args)
}
