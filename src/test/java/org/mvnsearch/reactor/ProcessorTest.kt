package org.mvnsearch.reactor

import org.junit.jupiter.api.Test
import reactor.core.publisher.DirectProcessor
import reactor.core.publisher.Flux

/**
 * Processor test
 *
 * @author linux_china
 */
class ProcessorTest {

    @Test
    fun testSpike() {
        val processor = DirectProcessor.create<Int>()
        processor.onNext(1);
        processor.onComplete()
    }


}