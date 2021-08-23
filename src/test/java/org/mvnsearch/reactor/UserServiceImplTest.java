package org.mvnsearch.reactor;

import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.reactor.MonoKt;
import kotlinx.coroutines.reactor.ReactorFlowKt;
import org.junit.jupiter.api.Test;
import org.mvnsearch.spring.KotlinCoroutineMethod;
import org.mvnsearch.spring.UserServiceImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;

/**
 * User service test
 *
 * @author linux_china
 */
public class UserServiceImplTest {
    private UserServiceImpl userService = new UserServiceImpl();

    @Test
    public void testMethods() {
        /*Method method = findMethod(userService.getClass(), "getNickById");
        KotlinCoroutineMethod coroutineMethod = new KotlinCoroutineMethod(method);
        System.out.println(coroutineMethod);
        KotlinCoroutineMethod flowMethod = new KotlinCoroutineMethod(findMethod(userService.getClass(), "getAllNames"));
        System.out.println(flowMethod);*/
        KotlinCoroutineMethod job1Method = new KotlinCoroutineMethod(findMethod(userService.getClass(), "getNicks"));
        System.out.println(job1Method);
    }


    @Test
    public void testConvertToReactor() throws Exception {
        Method method = findMethod(userService.getClass(), "getNickById");
        Mono<String> mono = MonoKt.mono(EmptyCoroutineContext.INSTANCE,
                (coroutineScope, continuation) -> {
                    Object obj = null;
                    try {
                        Object[] args = new Object[]{1, continuation};
                        obj = method.invoke(userService, args);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return obj;
                });
        mono.map(text -> {
            return "text: " + text;
        }).subscribe(text -> {
            System.out.println(text);
        });
        Thread.sleep(1000);
    }

    @Test
    public void testFlowToFlux() {
       /* userService.getAllNamesFlux().toStream().forEach(s -> {
            System.out.println(s);
        });*/
        Flux<String> flux = ReactorFlowKt.asFlux(userService.getAllNames());
        flux.toStream().forEach(s -> {
            System.out.println(s);
        });
    }

    public Method findMethod(Class<?> clazz, String methodName) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }

}
