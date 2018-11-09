package org.mvnsearch.rxjava

import org.junit.Test
import rx.lang.kotlin.subscribeBy
import rx.lang.kotlin.toObservable
import rx.subjects.PublishSubject

/**
 * RxKotlin test
 *
 * @author linux_china
 */

class RxKotlinTest {
    @Test
    fun testFirst() {
        val list = listOf("Alpha", "Beta", "Gamma", "Delta", "Epsilon")

        list.toObservable() // extension function for Iterables
            .filter { it.length >= 5 }
            .subscribeBy(// named arguments for lambda Subscribers
                onNext = { println(it) },
                onError = { it.printStackTrace() },
                onCompleted = { println("Done!") }
            )

    }

    @Test
    fun testPublisher() {
        var subject = PublishSubject.create<Int>()
        subject.subscribe {
            println("The number is $it")
        }
        subject.onNext(1)
        subject.onNext(2)
    }
}