package org.mvnsearch.reactor;

import com.google.common.base.Joiner;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.junit.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoProcessor;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

/**
 * reactor mon test
 *
 * @author linux_china
 */
public class ReactorMonoTest {

    @Test
    public void testEmptyWithError() {
        System.out.println(Mono.error(new Exception("goood")).switchIfEmpty(Mono.just("first")).block());
    }

    @Test
    public void testMonoProcessor() throws Exception {
        MonoProcessor<String> monoProcessor = MonoProcessor.create();
        monoProcessor.subscribe(System.out::println);
        monoProcessor.onNext("first");
        Thread.sleep(1000);
    }

    @Test
    public void testSpike() throws Exception {
        Mono.just("first")
                .doFinally(s -> System.out.println("success"))
                .subscribe(System.out::println);
        Thread.sleep(1000);
    }

    @Test
    public void testTerminal() throws Exception {
        MonoProcessor<Void> onClose = MonoProcessor.create();
        onClose.doOnTerminate(() -> {
            System.out.println("terminal1");
        }).subscribe();
        onClose.doOnTerminate(() -> {
            System.out.println("terminal2");
        }).subscribe();
        onClose.onComplete();
        Thread.sleep(1000);
    }

    @Test
    public void testCreate() {
        Mono.fromCallable(() -> "good").subscribe(System.out::println);
        Mono.just("good").subscribe(System.out::println);
        Mono.just("first").doOnNext(it -> {
            System.out.println(it);
        }).subscribe(it -> {
            System.out.println(it);
        });
    }

    @Test
    public void testListen() {
        System.out.println(Mono.just(1).block());
        System.out.println(Flux.just(1, 2, 3).collectList().block());
    }


    @Test
    public void testZipWith() {
        Mono<Integer> result = Mono.just(1);
        result.zipWith(Mono.just("demo"), new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) {
                return s + ":" + integer;
            }
        }).subscribe(t -> {
            System.out.println(t);
        });
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
