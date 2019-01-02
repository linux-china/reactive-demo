package org.mvnsearch.adapter;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.Set;

/**
 * Reactive Cache API
 *
 * @author linux_china
 */
public interface Cache<K, V> {

    Mono<V> get(K key);

    Mono<Void> put(K key, V value);

    Mono<Void> remove(K key);

    Mono<Boolean> containsKey(K key);

    Flux<Tuple2<K, V>> getAll(Set<? extends K> keys);

}
