package org.mvnsearch.coroutines

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.concurrent.thread
import kotlin.reflect.KProperty

/**
 * Created with IntelliJ IDEA.
 *
 * @author linux_china
 */
class DelegateTest {

    @Test
    fun testDemo() {
        var p by delegate2 {
            "hi"
        }
        p = "hello"
        println(p)
        Thread.sleep(1000)
    }
    
}

fun delegate2(initializer: () -> String): Delegate2 {
    val delegate2 = Delegate2(initializer())
    thread {
        runBlocking {
            launch {
                delegate2.stateFlow.collect {
                    print("ooo")
                }
            }
        }
    }
    return delegate2;
}


class Delegate2(value2: String) {
    val stateFlow = MutableStateFlow(value2)

    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return stateFlow.value;
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        this.stateFlow.value = value;
    }

}