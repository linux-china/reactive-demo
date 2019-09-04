package org.mvnsearch.coroutines

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
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
}