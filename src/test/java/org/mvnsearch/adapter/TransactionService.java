package org.mvnsearch.adapter;

import reactor.core.publisher.Mono;

/**
 * transaction service
 *
 * @author linux_china
 */
public class TransactionService {

    public Mono<Transaction> findById(Long id) {
        return Mono.just(new Transaction(id));
    }
}
