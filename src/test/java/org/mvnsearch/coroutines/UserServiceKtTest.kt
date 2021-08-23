package org.mvnsearch.coroutines

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.mvnsearch.spring.UserService

/**
 * Created with IntelliJ IDEA.
 *
 * @author linux_china
 */
class UserServiceKtTest {
    val userService: UserService = CoroutineInvocationHandler2.userServiceStub()//userServiceStub()

    @Test
    fun testFindByNick() = runBlocking {
        val nick = userService.getNickById(1)
        println(nick)
    }
}