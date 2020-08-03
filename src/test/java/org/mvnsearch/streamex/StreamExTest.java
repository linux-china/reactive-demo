package org.mvnsearch.streamex;

import one.util.streamex.StreamEx;
import org.junit.jupiter.api.Test;

public class StreamExTest {

    @Test
    public void testOperator() {
        StreamEx.of("first", "second").map(String::length)
                .forEach(System.out::println);
    }
}
