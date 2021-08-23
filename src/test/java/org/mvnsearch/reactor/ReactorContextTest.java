package org.mvnsearch.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.Context;

/**
 * Reactor context test  https://projectreactor.io/docs/core/release/reference/#context
 *
 * @author linux_china
 */
public class ReactorContextTest {

    @Test
    public void testContextOf() throws Exception {
        Mono<R2dbcConnection> connection = Mono.create(sink -> {
            sink.success(new R2dbcConnection());
        });
        Mono.defer(() -> {
            return connection.flatMap(connection1 -> {
                return connection1.beginTransaction().subscriberContext(Context.of(R2dbcConnection.class, connection1));
            });
        })
                .thenReturn("goood").flatMap(s -> {
            return Mono.deferWithContext(context -> {
                return Mono.just("second");
            });
        })
                .subscribe(demo -> {
                    System.out.println(demo);
                });
        Thread.sleep(1000);
    }

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
        Mono.deferContextual(ctx -> Mono.just(ctx.get("nick")))
                .subscriberContext(Context.of("nick", "linux_china"))
                .subscribe(nick -> {
                    System.out.println(nick);
                });
        Thread.sleep(1000);
    }

    @Test
    public void testThreadLocal() throws Exception {
        ThreadLocal<String> userThreadLocal = new ThreadLocal<>();
        userThreadLocal.set("yourNick");
        MutableContext context = new MutableContext();
        context.put("nick", userThreadLocal.get());
        Mono.deferWithContext(ctx -> Mono.just(ctx.get("nick")))
                .subscriberContext(context)
                .subscribe(text -> {
                    System.out.println(text);
                });
        Thread.sleep(1000);
    }

    @Test
    public void testScheduleHook() throws Exception {
        Schedulers.onScheduleHook("mdc", runnable -> () -> {
            System.out.println("before hook");
            runnable.run();
            System.out.println("after hook");
        });
        Mono.just("first")
                .doOnNext(text -> {
                    System.out.println("Thread:" + Thread.currentThread().getName());
                    System.out.println("next");
                })
                .subscribeOn(Schedulers.immediate())
                .subscribe(text -> {
                    System.out.println("Thread:" + Thread.currentThread().getName());
                    System.out.println(text);
                });

        Thread.sleep(1000);
    }

    @Test
    public void testContextFromOutside() {
        MutableContext context = new MutableContext();
        context.put("greeting", "Hello");
        String name = monoWithContext("Jackie").subscriberContext(context).block();
        System.out.println(name);
    }


    public Mono<String> monoWithContext(String name) {
        return Mono.deferWithContext((context) -> {
            return context.get("greeting");
        }).map((greeting) -> {
            return greeting + " " + name;
        });
    }
}
