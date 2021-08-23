package org.mvnsearch.flow


import kotlinx.coroutines.reactive.asFlow
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import java.util.concurrent.Flow
import java.util.concurrent.SubmissionPublisher

/**
 * Java Flow test
 *
 * @author linux_china
 */
class FlowTest {

    @Test
    fun testSpike() {
        val pub = SubmissionPublisher<Long>()
        pub.consume { println(it) }
        pub.submit(11L)
        Thread.sleep(1000)
    }

    @Test
    fun testSecond() {
        Flow.Publisher<String> { }
    }

    @Test
    fun testFluxToFlow() {
        Flux.just("1", "2").asFlow();
    }

    @Test
    fun testMutableFlow() {

    }
}

