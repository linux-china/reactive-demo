package org.mvnsearch.reactor.blockhound;

import org.junit.Test;
import reactor.blockhound.BlockHound;
import reactor.core.publisher.Mono;

import java.security.SecureRandom;
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
    public void testBlockDetect() throws Exception {
        Mono.delay(Duration.ofSeconds(1))
                .doOnNext(it -> {
                    try {
                        byte[] demo = new byte[128];
                        (new SecureRandom()).nextBytes(demo);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).subscribe();
        Thread.sleep(3000);
    }
}
