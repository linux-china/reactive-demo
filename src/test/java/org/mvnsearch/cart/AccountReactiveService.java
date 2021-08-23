package org.mvnsearch.cart;

import reactor.core.publisher.Mono;

/**
 * account service
 *
 * @author linux_china
 */
public interface AccountReactiveService {

    Mono<Account> findAccountById(Long id);
}
