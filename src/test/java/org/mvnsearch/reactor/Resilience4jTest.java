package org.mvnsearch.reactor;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.reactor.ratelimiter.operator.RateLimiterOperator;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * resilience4j with Reactor test
 *
 * @author linux_china
 */
public class Resilience4jTest {

    @Test
    public void testLimiter() throws Exception {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitRefreshPeriod(Duration.ofMillis(1000))
                .limitForPeriod(2)
                .build();
        RateLimiter rateLimiter = RateLimiter.of("rpcLimiter", config);
        for (int i = 0; i < 10; i++) {
            Mono<String> stringMono = Mono.fromCallable(this::getNick)
                    .transformDeferred(RateLimiterOperator.of(rateLimiter));
            stringMono.subscribe(text -> {
                System.out.println(text);
            });
        }
        Thread.sleep(1000);
    }

    public String getNick() {
        return "nick";
    }


}
