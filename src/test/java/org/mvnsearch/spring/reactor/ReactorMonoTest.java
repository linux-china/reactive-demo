package org.mvnsearch.spring.reactor;

import org.junit.Test;
import reactor.core.publisher.Mono;

/**
 * reactor mon test
 *
 * @author linux_china
 */
public class ReactorMonoTest {

    @Test
    public void testCreate() {
        Mono.fromCallable(() -> "good").subscribe(System.out::println);
        Mono.just("good").subscribe(System.out::println);
    }

    @Test
    public void testListen() {
        
    }
}
