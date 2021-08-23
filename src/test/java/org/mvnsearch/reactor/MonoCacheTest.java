package org.mvnsearch.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * Mono cache test
 *
 * @author linux_china
 */
public class MonoCacheTest {
    Map<String, String> cache = new HashMap<>();

    @Test
    public void testCacheOperate() throws Exception {
        Mono.just("first").doOnNext(s -> {
            cache.put("first", "第一");
        }).subscribe(t -> {
            System.out.println(t);
        });

        if (cache.containsKey("first")) {
            Mono.just(cache.get("first")).subscribe(t -> {
                System.out.println(t);
            });
        }
        Thread.sleep(1000);
    }
}
