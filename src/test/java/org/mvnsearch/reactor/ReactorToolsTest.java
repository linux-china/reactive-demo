package org.mvnsearch.reactor;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.tools.agent.ReactorDebugAgent;

/**
 * reactor tools test
 *
 * @author linux_china
 */
public class ReactorToolsTest {


    @BeforeAll
    public static void setUp() {
        ReactorDebugAgent.init();
    }

    @Test
    public void testDemo() throws Exception {
        Flux.range(0, 5)
                .single().subscribe();
        Thread.sleep(1000);
    }
}
