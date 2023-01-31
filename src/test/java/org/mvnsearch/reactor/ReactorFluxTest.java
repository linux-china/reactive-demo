package org.mvnsearch.reactor;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.context.ApplicationListener;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import reactor.core.publisher.*;
import reactor.core.scheduler.Schedulers;
import reactor.test.publisher.TestPublisher;
import reactor.util.context.Context;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * reactor test
 *
 * @author linux_china
 */
public class ReactorFluxTest {

    @Test
    public void testFinally() throws Exception {
        Flux.just("abc", "123").doFinally(signalType -> {
            System.out.println("finally");
        }).subscribe(s -> {
            System.out.println(s);
        });
        Thread.sleep(1000);
    }

    @Test
    public void testReadFile() throws Exception {
        Flux<String> lines = Flux.using(
                () -> Files.lines(Path.of("justfile"), StandardCharsets.UTF_8),
                Flux::fromStream,
                Stream::close
        );
        lines.subscribe(System.out::println);
        Thread.sleep(1000);
    }

    @Test
    public void testConnectableFlux() throws Exception {
        ConnectableFlux<String> flux = Flux.just("1", "2").publish();
        flux.subscribe(seq -> {
            System.out.println("sub1:" + seq);
        });
        System.out.println("sleep1");
        Thread.sleep(1000);
        System.out.println("sleep2");
        flux.subscribe(seq -> {
            System.out.println("sub2:" + seq);
        });
        flux.connect();
        Thread.sleep(2000);
    }

    @Test
    public void testStartWith() throws Exception {
        Flux.just("second", "third").startWith("first").subscribe(System.out::println);
        Thread.sleep(1000);

        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
    }

    @Test
    public void testDoFirst() throws Exception {
        Flux.just(1, 2, 3)
                .doFirst(() -> {
                    System.out.println("first");
                })
                .doOnTerminate(() -> {
                    System.out.println("finally");
                })
                .subscribe(number -> {
                    System.out.println(number);
                });
        Thread.sleep(1000);
    }

    @Test
    public void testTakeFirst() throws Exception {
        Flux.just("id,name", "1,leijuan", "2,juven").switchOnFirst((signal, stringFlux) -> {
            System.out.println("First: " + signal.get());
            return stringFlux.skip(1);
        }).subscribe(text -> {
            System.out.println(text);
        });
        Thread.sleep(1000);
    }

    @Test
    public void testDelaySequence() throws Exception {
        DirectProcessor<Object> processor = DirectProcessor.create();
        System.out.println(LocalDateTime.now());
        processor.delaySequence(Duration.ofSeconds(2)).subscribe(t -> {
            System.out.println(LocalDateTime.now() + ": " + t);
        });
        processor.onNext(1);
        processor.onNext(2);
        System.out.println(LocalDateTime.now());
        Thread.sleep(1000);
        processor.onNext(3);
        processor.onNext(4);
        Thread.sleep(5000);
    }

    /**
     * flux simple create, such as from array, list
     */
    @Test
    public void testSimpleCreate() {
        Flux<String> flux = Flux.just("red", "White", "blue").delayElements(Duration.ofMillis(100));
        flux.map(String::toUpperCase).subscribe(System.out::println);
        flux.filter(str -> str.length() > 3).map(String::toLowerCase).subscribe(System.out::println);
    }

    @Test
    public void testMergeMonAndFlux() throws Exception {
        Flux.just(1).mergeWith(Flux.just(2, 3, 4)).subscribe(System.out::println);
        Thread.sleep(1000);
    }

    @Test
    public void testDefer() throws Exception {
        Flux.defer(() -> Flux.just("1", "2")).subscribe(t -> System.out.println(t));
        Thread.sleep(1000);
    }

    @Test
    public void testContext() {
        Flux<Integer> flux = Flux.just(1, 2); //1
        Flux<String> stringFlux = flux.flatMap(i -> {
            return Mono.deferContextual(contextView -> {
                int pid = contextView.get("pid");
                return Mono.just("pid:" + pid + ",value:" + i);
            });
        });
        stringFlux.contextWrite(Context.of("pid", 1))
                .subscribe(System.out::println);
    }

    @Test
    public void testSwitchOnFirst() throws Exception {
        Flux.just(1, 2, 3).switchOnFirst((BiFunction<Signal<? extends Integer>, Flux<Integer>, Publisher<?>>) (signal, integerFlux) -> {
            System.out.println("signal:" + signal.get());
            return integerFlux.skip(1);
        }).subscribe(num -> System.out.println(num));
        Thread.sleep(1000);
    }

    @Test
    public void testBatch() {
        Flux<Integer> flux = Flux.just(1, 2, 3, 4, 5);
        flux.buffer(2).map(list1 -> {
            System.out.println("size:" + list1);
            return list1.size();
        }).subscribe(System.out::println);
    }

    @Test
    public void testTestPublisher() {
        TestPublisher<Integer> publisher = TestPublisher.<Integer>create();
        publisher.subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(10);
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println(integer);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
        publisher.next(1);
        publisher.next(2);
    }


    @Test
    public void testSchedulers() throws Exception {
        Flux.generate(
                        AtomicLong::new,
                        (state, sink) -> {
                            long i = state.getAndIncrement();
                            sink.next("3 x " + i + " = " + 3 * i);
                            System.out.println("Generate thread:" + Thread.currentThread().getName());
                            if (i == 10) sink.complete();
                            return state;
                        })
                .publishOn(Schedulers.parallel())
                .subscribeOn(Schedulers.single())
                .subscribe(t -> {
                    System.out.println("Subscribe thread:" + Thread.currentThread().getName());
                    System.out.println(t);
                });


        Thread.sleep(1000);
    }

    /**
     * flux generate from  Consumer<SynchronousSink<T>> generator
     */
    @Test
    public void testGenerate() {
        Flux<String> flux = Flux.generate(
                AtomicLong::new,
                (state, sink) -> {
                    long i = state.getAndIncrement();
                    sink.next("3 x " + i + " = " + 3 * i);
                    if (i == 10) sink.complete();
                    return state;
                });
        flux.subscribe(System.out::println);
    }

    @Test
    public void testCreate() throws Exception {
        EventBus eventBus = new EventBus();
        Flux<String> objectFlux = Flux.create(sink -> {
            eventBus.register(new EventListener() {
                @Subscribe
                public void stringEvent(String event) {
                    sink.next(event);
                }
            });
        });
        objectFlux.subscribe(System.out::println);
        eventBus.post("first");
        eventBus.post("second");
        Thread.sleep(100);
        System.out.println("wake up");
        eventBus.post("three");
        eventBus.post("four");
        Thread.sleep(10000);
    }

    @Test
    public void testPublisher() throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext();
        Flux<List<String>> upstreamFlux = Flux.create(sink -> {
            context.addApplicationListener((ApplicationListener<UpstreamEvent>) upstreamEvent -> sink.next(upstreamEvent.getIpList()));
        });
        context.refresh();
        upstreamFlux.subscribe(System.out::println);
        context.publishEvent(new UpstreamEvent(Arrays.asList("127", "128")));
        Thread.sleep(10000);
    }

    @Test
    public void testZip() {
        Flux.just(1, 2).zipWith(Flux.just(3, 4)).subscribe(System.out::println);
    }

    @Test
    public void testRepeat() throws Exception {
        Flux.just(1, 3).repeat(2).subscribe(System.out::println);
    }

    @Test
    public void testWindow() {
        Flux.just(1, 2, 3, 4, 5)
                .window(2)
                .subscribe(flux -> flux.count().subscribe(System.out::println));
    }

    @Test
    public void testTakeWhile() throws Exception {
        Flux.just(1, 2, 3, 4, 5)
                .takeUntil(number -> number < 3)
                .subscribe(System.out::println);
        Thread.sleep(1000);
    }


    @Test
    public void testBuffer() {
        Flux<String> sequences = Flux.just("1", "2", "3", "4", "5");
        sequences.buffer(2).flatMap(strings -> {
            System.out.println("length:" + strings.size());
            return Flux.fromStream(strings.stream().map(i -> i + ":"));
        }).subscribe(System.out::print);
    }

    @Test
    public void testTerminal() {
        Flux<String> names = Flux.just("one", "two", "three");
        names.map(String::toUpperCase)
                .collect(Collectors.joining(","))
                .subscribe(System.out::println);
    }

    @Test
    public void testThenMany() throws Exception {
        Function<Flux<String>, Flux<String>> filterAndMap =
                f -> f.filter(color -> !color.equals("orange"))
                        .map(String::toUpperCase);

        Flux.just(1, 2, 3)
                .mergeWith(Flux.just(4, 5))
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(t -> System.out.println("on:" + t))
                .subscribe(System.out::println);
        Thread.sleep(7000);
    }

}
