package org.mvnsearch.rxjava;

import org.junit.Test;
import rx.Observable;
import rx.Single;
import rx.Subscriber;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

import java.util.concurrent.TimeUnit;

/**
 * rxjava 1.x test
 *
 * @author linux_china
 */
public class RxJavaTest {

    @Test
    public void testSingle() {
        Single.just("Jacky").subscribe(System.out::println);
    }

    @Test
    public void testEmit() {
        PublishSubject<String> subject = PublishSubject.create();
        subject.subscribe(System.out::println);
        subject.onNext("first");
        subject.onNext("second");
    }

    @Test
    public void testSpike() {
        Observable.just(1, 2, 3)
                .map(integer -> 1 + integer)
                .subscribe(new Subscriber<Integer>() {
                    public void onCompleted() {
                        System.out.println("Completed");
                    }

                    public void onError(Throwable throwable) {
                        System.out.println(throwable.getMessage());
                    }

                    public void onNext(Integer num) {
                        System.out.println(num);
                    }
                });
        System.out.println("=================");
        Observable.just(1, 2, 3).subscribe(System.out::println);
    }

    @Test
    public void testInterval() throws Exception {
        Observable.interval(1, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                System.out.println(aLong);
            }
        });
        Thread.sleep(5000);
    }
}
