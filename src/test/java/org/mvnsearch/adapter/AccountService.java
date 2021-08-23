package org.mvnsearch.adapter;

import reactor.core.publisher.Mono;

/**
 * Created with IntelliJ IDEA.
 *
 * @author linux_china
 */
public interface AccountService {

    Mono<Account> findAccountById(long id);

    Mono<Account> findAccountByUUID(String uuid);

    Mono<Account> findAccountByEmail(String email);

    Mono<Account> findAccountByMobile(String mobile);

}
