package org.mvnsearch.spring.reactor;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

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
    public void testCreate() {
        Flux<String> objectFlux = Flux.create(sink -> {
            sink.next("good");
        });
        Flux<List<String>> buffer = objectFlux.buffer();
    }
}
