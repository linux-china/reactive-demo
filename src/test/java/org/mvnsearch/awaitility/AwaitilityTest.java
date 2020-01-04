package org.mvnsearch.awaitility;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.awaitility.Awaitility.await;

/**
 * Awaitility test
 *
 * @author linux_china
 */
public class AwaitilityTest {

    @Test
    public void testOperation() {
        await().timeout(Duration.ofSeconds(1));
    }
}
