package org.mvnsearch.coroutines

import org.mvnsearch.spring.UserService
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED
import kotlin.coroutines.resume

class CoroutineInvocationHandler : InvocationHandler {

    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any {
        val lastArg = args?.lastOrNull()
        if (lastArg is Continuation<*>) {
            val cont = lastArg as Continuation<Any>
            val argsButLast = args.take(args.size - 1)
            doSomethingWith(method, argsButLast, onComplete = { result: Any ->
                cont.resume(result)
            })
            return COROUTINE_SUSPENDED
        } else {
            return 0
        }
    }
}

fun userServiceStub(): UserService {
    return Proxy.newProxyInstance(
        UserService::class.java.classLoader, arrayOf<Class<*>>(UserService::class.java),
        CoroutineInvocationHandler()
    ) as UserService
}

fun doSomethingWith(method: Method?, argsButLast: List<Any>, onComplete: (Any) -> Unit) {
    onComplete.invoke("demo")
}
