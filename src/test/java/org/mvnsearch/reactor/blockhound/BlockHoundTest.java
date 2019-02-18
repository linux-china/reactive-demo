package org.mvnsearch.reactor.blockhound;

import org.junit.Test;
import reactor.BlockHound;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * block hound test
 *
 * @author linux_china
 */
public class BlockHoundTest {
    static {
        BlockHound.install();
    }

    @Test
    public void testBlockDetect() {
        Mono.delay(Duration.ofSeconds(1))
                .doOnNext(it -> {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
                .block();
    }
}
