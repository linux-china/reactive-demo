package org.mvnsearch.reactor;

import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 *
 * @author linux_china
 */
public class R2dbcConnection {
    private String uuid = UUID.randomUUID().toString();
    Mono<String> con = Mono.create(sink -> {
        sink.success("good");
    });

    public Mono<Void> beginTransaction() {
        return Mono.empty();
    }

    public Mono<Void> commit() {
        return Mono.empty();
    }

    public Mono<String> select() {
        return Mono.just("demo");
    }
}
