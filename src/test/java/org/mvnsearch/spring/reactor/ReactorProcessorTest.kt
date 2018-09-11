package org.mvnsearch.spring.reactor

import org.junit.Test
import reactor.core.publisher.DirectProcessor

/**
 * reactor processor test
 *
 * @author linux_china
 */

class ReactorProcessorTest {

    @Test
    fun testDirectProcessor() {
        val processor = DirectProcessor.create<String>()
        processor.subscribe { println(it) }
        processor.onNext("good")
        processor.onNext("morning")
    }
}