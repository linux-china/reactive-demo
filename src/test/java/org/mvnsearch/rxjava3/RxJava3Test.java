package org.mvnsearch.rxjava3;

import io.reactivex.rxjava3.core.Flowable;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * RxJava 3 test
 *
 * @author linux_china
 */
public class RxJava3Test {

    @Test
    public void testSpike() {
        Flowable.just("Hello world").subscribe(System.out::println);
    }

    @Test
    public void testConvert() throws Exception {
        for (Method method : this.getClass().getDeclaredMethods()) {
            System.out.println(method.getReturnType().getCanonicalName());
            Type genericReturnType = method.getGenericReturnType();
            genericReturnType.getTypeName();
        }
    }

}
