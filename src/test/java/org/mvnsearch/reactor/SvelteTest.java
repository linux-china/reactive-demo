package org.mvnsearch.reactor;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import org.junit.jupiter.api.Test;

/**
 * Created with IntelliJ IDEA.
 *
 * @author linux_china
 */
public class SvelteTest {

    @Test
    public void testComponentProp() throws Exception {
        BehaviorSubject<Integer> subject = BehaviorSubject.create();
        subject.subscribe(num -> {
            System.out.println("subscriber1: " + num);
        });
        subject.subscribe(num -> {
            System.out.println("subscriber2: " + num);
        });
        subject.onNext(1);
        Thread.sleep(2000);
    }

    @Test
    public void testAwait() throws Exception {
        Single<Integer> single = Single.just(1);
        single.subscribe(num -> {
            System.out.println("subscriber1: " + num);
        });
        single.subscribe(num -> {
            System.out.println("subscriber2: " + num);
        });
        Thread.sleep(2000);
    }
}
