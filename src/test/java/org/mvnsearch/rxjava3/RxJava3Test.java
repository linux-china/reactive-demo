package org.mvnsearch.rxjava3;

import io.reactivex.rxjava3.core.Flowable;
import org.junit.Test;

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
}
