package org.mvnsearch.mutiny;

import io.smallrye.mutiny.Multi;
import org.junit.jupiter.api.Test;

/**
 * SmallRye Mutiny test
 *
 * @author linux_china
 */
public class SmallRyeMutinyTest {

    @Test
    public void testOperation() {
        Multi.createFrom().items("hello", "world")
                .onItem().transform(s -> s.toUpperCase() + " ")
                .onCompletion().continueWith("!")
                .subscribe().with(System.out::print);
    }
}
