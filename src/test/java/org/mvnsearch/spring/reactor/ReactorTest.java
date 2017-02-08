package org.mvnsearch.spring.reactor;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

/**
 * reactor test
 *
 * @author linux_china
 */
public class ReactorTest {

    @Test
    public void testFlux() {
        Flux<String> flux = Flux.just("red", "white", "blue");
        flux.map(String::toUpperCase)
                .subscribe(System.out::println);
    }
}
