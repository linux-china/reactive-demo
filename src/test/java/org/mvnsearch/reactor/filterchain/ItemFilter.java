package org.mvnsearch.reactor.filterchain;

import reactor.core.publisher.Mono;

/**
 * item filter
 *
 * @author linux_china
 */
public interface ItemFilter {

    Mono<Boolean> test(Object item);
}
