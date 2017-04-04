package org.mvnsearch.spring.reactor

import org.junit.Test
import reactor.core.publisher.test
import reactor.core.publisher.toMono

/**
 * reactor kotlin test
 *
 * @author linux_china
 */

class ReactorKotlinTest {

    @Test
    fun testMono() {
        val mono = "foo".toMono()
        mono.subscribe() {
            println(it)
        }
        mono.test().expectNext("foo").verifyComplete();
    }
}