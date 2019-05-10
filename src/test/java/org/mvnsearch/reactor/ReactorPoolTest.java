package org.mvnsearch.reactor;

import org.junit.Test;
import reactor.core.publisher.Mono;
import reactor.pool.Pool;
import reactor.pool.PoolBuilder;
import reactor.test.publisher.PublisherProbe;

/**
 * reactor pool test
 *
 * @author linux_china
 */
public class ReactorPoolTest {

    @Test
    public void testPool() throws Exception{
        PublisherProbe<Integer> probe = PublisherProbe.of(Mono.fromCallable(() -> {
            return 1;
        }));
        Pool<Integer> pool = PoolBuilder.from(probe.mono()).build();
        pool.withPoolable(resource->{
            return Mono.just(resource+1);
        }).subscribe(System.out::println);
        Thread.sleep(1000);
    }
}
