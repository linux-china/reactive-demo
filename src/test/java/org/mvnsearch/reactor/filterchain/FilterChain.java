package org.mvnsearch.reactor.filterchain;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author linux_china
 */
public class FilterChain {
    private List<ItemFilter> filters = new ArrayList<>();

    public void add(ItemFilter filter) {
        this.filters.add(filter);
    }

    public Mono<Void> execute(Object item) {
        return Flux.fromIterable(filters)
                .filterWhen(filter -> filter.test(item))
                .then();
    }
}
