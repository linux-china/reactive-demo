package org.mvnsearch.reactor;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

/**
 * Created with IntelliJ IDEA.
 *
 * @author linux_china
 */
public class RetryTest {
    @Test
    public void testRetry() throws Exception {
        Mono.fromCallable(() -> {
            System.out.println("call:" + System.currentTimeMillis());
            return "demo";
        })
                .retry(2)
                .subscribe(text -> {
                    System.out.println("text:" + text);
                });
        Thread.sleep(2000);
    }

    @Test
    public void testRetryDefer() {
        System.out.println(Publisher.class.isAssignableFrom(Mono.class));
    }

    private Supplier<Mono<String>> textSupply() {
        return () -> {
            System.out.println("call");
            return Mono.error(new Exception("bad"));
        };
    }
}
