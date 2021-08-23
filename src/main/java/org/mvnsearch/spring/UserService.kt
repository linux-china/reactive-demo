package org.mvnsearch.spring

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.reactor.asFlux
import reactor.core.publisher.Flux

/**
 * User service
 *
 * @author linux_china
 */
interface UserService {

    suspend fun job1()

    suspend fun getNickById(id: Int): String
    suspend fun getNicks(id: Int): List<String>?

    fun getAllNames(): Flow<String>

    fun findNamesByType(type: Int): Flow<String>
}


class UserServiceImpl : UserService {

    override suspend fun job1() {
        println("job1")
    }

    override suspend fun getNickById(id: Int): String {
        return "nick $id"
    }

    override suspend fun getNicks(id: Int): List<String>? {
           return null
        }

    override fun getAllNames(): Flow<String> {
        return arrayOf("first", "second").asFlow();
    }

    override fun findNamesByType(type: Int): Flow<String> {
        return arrayOf("first", "second").asFlow();
    }

    fun getAllNamesFlux(): Flux<String> {
        return getAllNames().asFlux()
    }
}