package org.mvnsearch.rxjava;

import org.junit.Test;
import rx.Observable;

/**
 * Observable test
 *
 * @author linux_china
 */
public class ObservableTest {

    @Test
    public void testSubscribe() {
        Observable.just(1, 2).subscribe(System.out::println);
    }
}
