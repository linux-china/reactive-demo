package org.mvnsearch.eventloop;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * executor service
 *
 * @author linux_china
 */
public class ExecutorServiceTest {
    private static ExecutorService executorService = new ThreadPoolExecutor(1, 3, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(3));

    @Test
    public void testExecute() throws Exception {
        for (int i = 2; i < 10; i++) {
            executorService.execute(delayRunnable(2));
        }
        Thread.sleep(10000);
    }

    @Test
    public void testFlux() throws Exception {
        for (int i = 1; i < 100; i++) {
            Mono.just("111@111.com").flatMap(this::send).subscribe(t -> {
                System.out.println(t);
                System.out.println("Thread:" + Thread.currentThread().getName());
            });
        }
        Thread.sleep(10000);

    }

    private Runnable delayRunnable(int seconds) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(seconds);
                    System.out.println("running");
                } catch (Exception e) {

                }
            }
        };
    }

    private Mono<String> send(String email) {
        return Mono.delay(Duration.ofSeconds(5)).map(t -> email);
    }


}
