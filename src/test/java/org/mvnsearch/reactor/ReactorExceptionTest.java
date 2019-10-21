package org.mvnsearch.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * Reactor Exception test
 *
 * @author linux_china
 */
public class ReactorExceptionTest {
    @Test
    public void testExceptionDuringMap() throws Exception {
        Mono.just("https://www.taobao.com/").handle((text, sink) -> {
            try {
                sink.next(new URI(text));
            } catch (Exception e) {
                sink.error(e);
            }
        }).subscribe(uri -> {
            System.out.println(uri.toString());
        });
        Thread.sleep(100);
    }
}
