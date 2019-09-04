package org.mvnsearch.rxjava

import io.reactivex.rxkotlin.toObservable
import org.junit.Test
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
            .subscribe {
                println(it)
            }

    }

    @Test
    fun testPublisher() {
        val subject = PublishSubject.create<Int>()
        subject.subscribe {
            println("The number is $it")
        }
        subject.onNext(1)
        subject.onNext(2)
    }
}