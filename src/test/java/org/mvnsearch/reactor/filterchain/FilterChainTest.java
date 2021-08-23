package org.mvnsearch.reactor.filterchain;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

/**
 * filter chain test
 *
 * @author linux_china
 */
public class FilterChainTest {

    @Test
    public void testFilter() throws Exception{
        FilterChain chain = new FilterChain();
        chain.add(item -> {
            System.out.println("filter1: " + item);
            return Mono.just(true);
        });
        chain.add(item -> {
            System.out.println("filter2: " + item);
            return Mono.just(false);
        });
        chain.add(item -> {
            System.out.println("filter3: " + item);
            return Mono.just(true);
        });

        chain.execute("good").subscribe();
        Thread.sleep(1000);
    }

}
