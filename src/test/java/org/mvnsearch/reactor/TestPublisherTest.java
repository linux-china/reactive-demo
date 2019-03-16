package org.mvnsearch.reactor;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.publisher.PublisherProbe;
import reactor.test.publisher.TestPublisher;

/**
 * test publisher test
 *
 * @author linux_china
 */
public class TestPublisherTest {

    @Test
    public void testFlux() throws Exception {
        TestPublisher<Integer> testPublisher = TestPublisher.create();
        testPublisher.mono().subscribe(t -> System.out.println(t));
        testPublisher.flux().subscribe(t -> System.out.println(t));
        testPublisher.next(1);
        testPublisher.next(2);
        testPublisher.assertSubscribers();
        Thread.sleep(1000);
    }

    @Test
    public void testProbe() {
        PublisherProbe<Integer> probe = PublisherProbe.of(Flux.just(1, 2));
    }
}
