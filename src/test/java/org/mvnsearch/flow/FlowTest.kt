package org.mvnsearch.flow

import org.junit.Test

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
}