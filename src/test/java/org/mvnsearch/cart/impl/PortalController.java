package org.mvnsearch.cart.impl;

import org.mvnsearch.cart.Account;
import org.mvnsearch.cart.AccountReactiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

/**
 * Created with IntelliJ IDEA.
 *
 * @author linux_china
 */
public class PortalController {
    @Autowired
    private AccountReactiveService reactiveService;

    @RequestMapping("/we")
    public Mono<Account> findAccount(Long id) {
       return reactiveService.findAccountById(id);
    }
}
