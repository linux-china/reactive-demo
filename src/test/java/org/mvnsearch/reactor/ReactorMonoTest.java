package org.mvnsearch.reactor;

import com.google.common.base.Joiner;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoProcessor;
import reactor.util.context.Context;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * reactor mon test
 *
 * @author linux_china
 */
public class ReactorMonoTest {

    @Test
    public void testTransform() throws Exception {
        Mono.just("first").transformDeferred(origin -> {
            return Mono.just("demo");
        }).subscribe(text -> {
            System.out.println(text);
        });
        Thread.sleep(1000);
    }

    @Test
    public void testContext() throws Exception {
        Mono<String> first = Mono.just("demo");
        Mono.deferContextual(context -> {
            System.out.println(context.get("name").toString());
            return first;
        }).contextWrite(Context.of("name", "value"))
                .subscribe(text -> {
                    System.out.println(text);
                });
        Thread.sleep(1000);
    }

    @Test
    public void testTimeout() throws Exception {
        Mono.just("first").delayElement(Duration.ofSeconds(5))
                .timeout(Duration.ofSeconds(2))
                .doOnError(throwable -> {
                    System.out.println(throwable.getClass());
                }).subscribe();
        Thread.sleep(10000);
    }

    @Test
    public void testOnSuccess() throws Exception {
        Mono.empty().doOnSuccess(o -> {
            System.out.println("goodo");
        }).subscribe();

        Thread.sleep(1000);
    }

    @Test
    public void testTryFinally() throws Exception {
        Mono.just("demo").doOnSubscribe(subscription -> {
            System.out.println("Begin to subscribe");
        }).doFinally((signalType -> {
            System.out.println("finished");
        })).subscribe();
        Thread.sleep(100);
    }

    @Test
    public void testDemo() throws Exception {
        Mono.empty().doOnSuccess(data -> {
            System.out.println(data);
        }).subscribe();
        Thread.sleep(1000);
    }

    @Test
    public void testCache() throws Exception {
        Mono<String> user = Mono.<String>create(monoSink -> {
            System.out.println("Only Once");
            monoSink.success("nick");
        }).cache(Duration.ofSeconds(2));
        user.subscribe(t -> {
            System.out.println(t);
        });
        user.subscribe(t -> {
            System.out.println(t);
        });
        Thread.sleep(3000);
        user.subscribe(t -> {
            System.out.println(t);
        });
        user.subscribe(t -> {
            System.out.println(t);
        });
        Thread.sleep(1000);
    }

    @Test
    public void testDefer() throws Exception {
        Mono<String> nick = getNick();
        System.out.println("nick invoked");
        Mono<String> defer = Mono.defer(this::getNick);
        defer.subscribe(text -> {
            System.out.println(text);
        });
        defer.subscribe(text -> {
            System.out.println(text);
        });
        Thread.sleep(1000);
    }

    @Test
    public void delayUntil() throws Exception {
        Mono<Integer> demo = Mono.just(1).delayUntil(new Function<Integer, Publisher<?>>() {
            @Override
            public Publisher<?> apply(Integer number) {
                return Mono.just(number).delay(Duration.ofSeconds(1));
            }
        });
        demo.subscribe(num -> {
            System.out.println(num);
        });
        System.out.println("good");
        Thread.sleep(2000);

    }

    public Mono<String> getNick() {
        System.out.println("get nick");
        return Mono.just("nick");
    }

    @Test
    public void testTimeOut() throws Exception {
        MonoProcessor.create((sink -> {
            //operation here
        })).timeout(Duration.ofSeconds(2)).doOnError((ex) -> {
            System.out.println(ex.getMessage());
        }).subscribe();
        Thread.sleep(5000);
    }

    @Test
    public void testOperations() throws Exception {
        Mono<Integer> r1 = Mono.just(1);
        Mono<Object> r2 = Mono.error(new Exception("Not a number"));
        Mono<Object> r3 = Mono.empty();
        Mono.empty()
                .doOnNext(num -> {
                    System.out.println(num);
                })
                .doOnError(error -> {
                    System.out.println("error");
                })
                .switchIfEmpty(Mono.fromRunnable(() -> {
                    System.out.println("empty");
                }))
                .subscribe();
        Thread.sleep(1000);
    }

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
        Mono.just(1).dematerialize().subscribe(t -> {
            System.out.println(t);
        });
        Thread.sleep(1000);
    }

    @Test
    public void testDynamicMono() {
        Mono<Integer> integerMono = Mono.fromCallable(() -> (new Random()).nextInt());
        System.out.println(integerMono.block());
        System.out.println(integerMono.block());
        System.out.println(integerMono.block());
        System.out.println(integerMono.block());
        System.out.println(integerMono.block());
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
