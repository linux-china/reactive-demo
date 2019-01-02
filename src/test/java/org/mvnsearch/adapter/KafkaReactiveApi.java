package org.mvnsearch.adapter;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Kafka reactive API
 *
 * @author linux_china
 */
public interface KafkaReactiveApi {

    Mono<Void> send(String topic, byte[] data);

    Flux<byte[]> subscribe(String topic);

    Flux<byte[]> subscribeWithGroup(String topic, String group);
}
