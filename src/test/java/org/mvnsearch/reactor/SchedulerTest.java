package org.mvnsearch.reactor;


import org.junit.jupiter.api.Test;
import reactor.core.Disposable;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.TimeUnit;

/**
 * Scheduler test
 *
 * @author linux_china
 */
public class SchedulerTest {
    private static Scheduler scheduler = Schedulers.single();

    @Test
    public void testRunNow() throws Exception {
        Disposable disposable = scheduler.schedule(() -> {
            System.out.println("good");
        });
    }

    @Test
    public void testDelay() throws Exception {
        Disposable disposable = scheduler.schedule(() -> {
            System.out.println("good");
        }, 2, TimeUnit.SECONDS);
        //disposable.dispose();
        Thread.sleep(3000);
    }

    @Test
    public void testPeriodicalCall() throws Exception {
        Disposable disposable = scheduler.schedulePeriodically(() -> {
            System.out.println("good");
        }, 0, 1, TimeUnit.SECONDS);
        Thread.sleep(9000);
    }
}
