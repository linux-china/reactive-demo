package org.mvnsearch.coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.junit.Test

/**
 * Coroutines Flow test
 *
 * @author linux_china
 */
class CoroutinesFlowTest {

    @Test
    fun testFlow() = runBlocking {
        val flowA = flowOf(1, 2, 3)
            .map { it + 1 } // Will be executed in ctxA
            .collect { println(it) }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testStateFlow() = runBlocking {
        val counterModel = CounterModel()
        launch {
            for (k in 1..3) {
                delay(100)
                counterModel.setName("name: $k")
            }
        }
        launch {
            counterModel.counter.collect { value -> println("New value: $value") }
        }
        launch {
            counterModel.counter.onEach {
                println("New2 value: $it")
            }.launchIn(GlobalScope)
        }
        delay(1000)
    }
}

@ExperimentalCoroutinesApi
class CounterModel {
    private val _counter = MutableStateFlow("") // private mutable state flow
    val counter: StateFlow<String> get() = _counter // publicly exposed as read-only state flow

    fun setName(name: String) {
        _counter.value = name
    }
}