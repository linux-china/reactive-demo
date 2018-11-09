package org.mvnsearch.spring.reactor;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.junit.Test;
import org.springframework.context.ApplicationListener;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * reactor test
 *
 * @author linux_china
 */
public class ReactorFluxTest {

    /**
     * flux simple create, such as from array, list
     */
    @Test
    public void testSimpleCreate() {
        Flux<String> flux = Flux.just("red", "white", "blue");
        flux.map(String::toUpperCase).doOnNext(System.out::println).subscribe();
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
        Flux.just(1, 2).zipWith(Flux.just(3, 4)).subscribe(t -> System.out.println(t));
    }

    @Test
    public void testRepeat() throws Exception {
        Flux.just(1, 3).repeat(2).subscribe(t -> System.out.println(t));
    }

    @Test
    public void testWindow() {
        Flux.just(1, 2, 3, 4, 5).window(2).subscribe(t -> t.count().subscribe(System.out::println));
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
                .subscribe(e -> System.out.println(e));
        Thread.sleep(7000);
    }

}
