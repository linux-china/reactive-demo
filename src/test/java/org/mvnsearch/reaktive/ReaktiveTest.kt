package org.mvnsearch.reaktive

import com.badoo.reaktive.observable.observableOf
import com.badoo.reaktive.observable.subscribe
import com.badoo.reaktive.single.singleOf
import com.badoo.reaktive.single.subscribe
import com.badoo.reaktive.subject.publish.PublishSubject
import org.junit.jupiter.api.Test

/**
 * reaktive test
 *
 * @author linux_china
 */
class ReaktiveTest {

    @Test
    fun testSpike() {
        val first = singleOf("First")
        first.subscribe {
            println(it)
        }
        Thread.sleep(1000)
    }

    @Test
    fun testFlow() {
        val flow = observableOf("First", "Second")
        flow.subscribe {
            println(it)
        }
        Thread.sleep(1000)
    }

    @Test
    fun testSubject() {
        val subject = PublishSubject<String>()
        subject.subscribe {
            println(it)
        }
        subject.onNext("first")
        subject.onNext("second")
        Thread.sleep(1000)
    }
}