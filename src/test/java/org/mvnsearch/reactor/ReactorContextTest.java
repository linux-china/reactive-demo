package org.mvnsearch.reactor;

import org.junit.Test;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

/**
 * Reactor context test  https://projectreactor.io/docs/core/release/reference/#context
 *
 * @author linux_china
 */
public class ReactorContextTest {

    @Test
    public void testContextSupport() throws Exception {
        Mono.just("Hello")
                .flatMap(s -> Mono.subscriberContext()
                        .map(ctx -> {
                            return s + " " + ctx.get("nick");
                        }))
                .subscriberContext(ctx -> ctx.put("nick", "Reactor"))
                .subscriberContext(Context.of("nic2","reactor"))
                .subscribe(t -> {
                    System.out.println(t);
                });
        Thread.sleep(1000);
    }
}
