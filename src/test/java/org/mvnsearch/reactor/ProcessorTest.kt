package org.mvnsearch.reactor

import org.junit.Test
import reactor.core.publisher.DirectProcessor

/**
 * Processor test
 *
 * @author linux_china
 */
class ProcessorTest {

    @Test
    fun testSpike() {
        val processor = DirectProcessor.create<Int>()
        processor.subscribe {
            println(it)
        }
        processor.onNext(1);
        processor.onComplete()
    }
}