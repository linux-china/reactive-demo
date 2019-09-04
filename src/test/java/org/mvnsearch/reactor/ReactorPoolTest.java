package org.mvnsearch.reactor;

import org.junit.Test;
import reactor.core.publisher.Mono;
import reactor.pool.Pool;
import reactor.pool.PoolBuilder;
import reactor.pool.PoolConfig;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * reactor pool test
 *
 * @author linux_china
 */
public class ReactorPoolTest {

    @Test
    public void testPool() throws Exception {

        AtomicInteger newCount = new AtomicInteger();
        PoolBuilder<Integer, PoolConfig<Integer>> builder = PoolBuilder
                //default maxUse is 5, but this test relies on it being 2
                .from(Mono.defer(() -> Mono.just(newCount.getAndIncrement())))
                .sizeBetween(2, 3);
        Pool<Integer> pool = ReactorPoolTest.<Integer>simplePoolFifo().apply(builder);
        pool.withPoolable(resource -> {
            return Mono.just(resource + 1);
        }).subscribe(System.out::println);
        Thread.sleep(1000);
    }

    static final <T> Function<PoolBuilder<T, ?>, Pool<T>> simplePoolFifo() {
        return new Function<PoolBuilder<T, ?>, Pool<T>>() {
            @Override
            public Pool<T> apply(PoolBuilder<T, ?> builder) {
                return (Pool<T>) builder.fifo();
            }

            @Override
            public String toString() {
                return "simplePool FIFO";
            }
        };
    }
}
