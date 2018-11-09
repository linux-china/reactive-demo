package org.mvnsearch.rxjava2;

import io.reactivex.Flowable;
import org.junit.Test;

/**
 * flowable test
 *
 * @author linux_china
 */
public class FlowableTest {

    @Test
    public void testFlowable() {
        Flowable.just("Hello").subscribe(t -> System.out.println(t));
    }
}
