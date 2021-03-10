package org.mvnsearch.mutiny;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.converters.uni.UniReactorConverters;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

/**
 * SmallRye Mutiny test
 *
 * @author linux_china
 */
public class SmallRyeMutinyTest {

    @Test
    public void testUniOperation() {
        Uni.createFrom().item("Hello")
                .onItem().transform(s -> s.toUpperCase() + " ")
                .subscribe().with(System.out::print);
    }

    @Test
    public void testMultiOperation() {
        Multi.createFrom().items("hello", "world")
                .onItem().transform(s -> s.toUpperCase() + " ")
                .onCompletion().continueWith("!")
                .subscribe().with(System.out::print);
    }

    @Test
    public void testUniMono() {
        Uni<String> uni = Uni.createFrom().item("Hello");
        Mono<String> mono = UniReactorConverters.<String>toMono().apply(uni);
        System.out.println(mono.block());
    }
}
