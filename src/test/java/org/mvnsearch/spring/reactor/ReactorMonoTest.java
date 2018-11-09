package org.mvnsearch.spring.reactor;

import com.google.common.base.Joiner;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.junit.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * reactor mon test
 *
 * @author linux_china
 */
public class ReactorMonoTest {

    @Test
    public void testCreate() {
        Mono.fromCallable(() -> "good").subscribe(System.out::println);
        Mono.just("good").subscribe(System.out::println);
    }

    @Test
    public void testListen() {
        System.out.println(Mono.just(1).block());
        System.out.println(Flux.just(1,2,3).collectList().block());
    }

    @Test
    public void testPublisher() throws Exception {
        EventBus eventBus = new EventBus();
        final Flux<List<String>> flux = Flux.create(sink -> {
            eventBus.register(new EventListener() {
                @Subscribe
                public void stringEvent(List<String> event) {
                    sink.next(event);
                }
            });
        });
        Publisher<List<String>> publisher = new Publisher<List<String>>() {
            @Override
            public void subscribe(Subscriber<? super List<String>> subscriber) {
                flux.subscribe(s -> {
                    subscriber.onNext(s);
                });
            }
        };
        publisher.subscribe(new Subscriber<List<String>>() {
            private Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {
                this.subscription = subscription;
                subscription.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(List<String> strings) {
                System.out.println(Joiner.on(',').join(strings));
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {

            }
        });
        eventBus.post(Arrays.asList("first"));
        Thread.sleep(1000);
        eventBus.post(Arrays.asList("second"));
        Thread.sleep(1000);
        eventBus.post(Arrays.asList("third"));
        Thread.sleep(10000);

    }
}
