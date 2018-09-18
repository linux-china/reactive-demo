package org.mvnsearch.spring.reactor;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicLong;
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
        flux.map(String::toUpperCase);
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
        Thread.sleep(10000);

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

}
