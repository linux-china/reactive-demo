package org.mvnsearch.spring.reactor;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
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
    public void testCreate() {
        Flux<String> objectFlux = Flux.create(sink -> {
            sink.next("good");
        });
        Flux<List<String>> buffer = objectFlux.buffer();
    }

    @Test
    public void testBuffer() {
        Flux<String> sequences = Flux.just("1", "2", "3", "4", "5");
        sequences.buffer(2).subscribe(new Consumer<List<String>>() {
            @Override
            public void accept(List<String> strings) {
                System.out.println(strings.size());
            }
        });
    }

    @Test
    public void testTerminal() {
        Flux<String> names = Flux.just("one", "two", "three");
        names.map(String::toUpperCase)
                .collect(Collectors.joining(","))
                .subscribe(System.out::println);
    }

}
