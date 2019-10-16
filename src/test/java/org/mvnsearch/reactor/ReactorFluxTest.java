package org.mvnsearch.reactor;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.junit.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.context.ApplicationListener;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;
import reactor.core.scheduler.Schedulers;
import reactor.test.publisher.TestPublisher;
import reactor.util.context.Context;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * reactor test
 *
 * @author linux_china
 */
public class ReactorFluxTest {

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
        Flux<String> flux = Flux.just("red", "White", "blue");
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
            return Mono.subscriberContext().map(ctx -> i + " pid: " +
                    ctx.getOrDefault("pid", 0));
        });
        stringFlux.subscriberContext(Context.of("pid", 1))
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
