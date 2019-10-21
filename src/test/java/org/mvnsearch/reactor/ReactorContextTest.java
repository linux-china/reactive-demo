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
        MutableContext context = new MutableContext();
        context.put("nic2", "Jackie");
        Mono.just("Hello")
                .flatMap(s -> Mono.subscriberContext()
                        .map(ctx -> {
                            return s + " " + ctx.get("nick");
                        }))
                .subscriberContext(ctx -> ctx.put("nick", "Reactor"))
                .subscriberContext(context)
                .subscribe(t -> {
                    System.out.println(t);
                });
        Thread.sleep(1000);
    }

    @Test
    public void testDeferWithContext() throws Exception {
        Mono.deferWithContext(ctx -> Mono.just(ctx.get("nick")))
                .subscriberContext(Context.of("nick", "linux_china"))
                .subscribe(nick -> {
                    System.out.println(nick);
                });
        Thread.sleep(1000);

    }

}
