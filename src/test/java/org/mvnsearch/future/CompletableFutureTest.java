package org.mvnsearch.future;


import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * completable future test   https://learning.oreilly.com/library/view/java-high-performance-apps/9781789130515/ch05s04.html
 *
 * @author linux_china
 */
public class CompletableFutureTest {
    ExecutorService es = Executors.newFixedThreadPool(10);

    @Test
    public void testCompletableFuture() throws Exception {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "jackie", es);
        future.whenCompleteAsync((s, throwable) -> {
            System.out.println(s);
        });
        Thread.sleep(1000);
    }
}
