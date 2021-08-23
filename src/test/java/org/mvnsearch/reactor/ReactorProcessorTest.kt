package org.mvnsearch.reactor

import org.junit.jupiter.api.Test
import reactor.core.publisher.DirectProcessor
import reactor.core.publisher.ReplayProcessor

/**
 * reactor processor test, please refer https://learning.oreilly.com/library/view/hands-on-reactive-programming/9781789135794/1e2fe499-843f-4e14-a5cb-0765ffed2f15.xhtml
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

    @Test
    fun testGetLastForSubscribe() {
        val processor = ReplayProcessor.cacheLast<String>()
        processor.subscribe { println("S1:$it") }
        processor.onNext("good")
        processor.onNext("morning")
        processor.subscribe { println("S2:$it") }
        val mono = processor.last();
        mono.subscribe {
            println("last:${it}")
        }
        Thread.sleep(1000)
    }
}