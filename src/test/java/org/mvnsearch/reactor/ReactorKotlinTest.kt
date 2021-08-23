package org.mvnsearch.reactor

import org.junit.jupiter.api.Test
import reactor.core.publisher.EmitterProcessor
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import reactor.test.test

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

    @Test
    fun testCallable() {
        Mono.fromCallable { "good" }.subscribe { println(it) }
    }

    @Test
    fun testPublisher() {
        val emitter = EmitterProcessor.create<Int>()
        emitter
            .map { it + 1 }
            .subscribe { println(it) }
        emitter.onNext(1)
        emitter.onNext(2)
    }

    @Test
    fun testDefer() {
        val lazyValue by lazy {
            println("computed!")
            "Hello"
        }
        Mono.just(lazyValue).subscribe { println(it) }
    }


    @Test
    fun testMonoGenerator() {
        Mono.fromSupplier { "demo" }.subscribe { }
    }

    @Test
    fun testException() {
        Mono.empty<String>()
            .thenReturn("first")
            .subscribe {
                println(it)
            }
        Thread.sleep(1000)
    }


}