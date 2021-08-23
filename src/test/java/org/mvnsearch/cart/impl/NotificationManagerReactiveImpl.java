package org.mvnsearch.cart.impl;

import org.mvnsearch.cart.Account;
import org.mvnsearch.cart.AccountReactiveService;
import org.mvnsearch.cart.EmailReactiveService;
import org.mvnsearch.cart.NotificationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * notification manager
 *
 * @author linux_china
 */
@Component
public class NotificationManagerReactiveImpl implements NotificationManager {
    private Logger log = LoggerFactory.getLogger(NotificationManagerReactiveImpl.class);
    private AccountReactiveService accountService;
    private EmailReactiveService emailService;
    private ExecutorService executor = new ThreadPoolExecutor(8, 8, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(200));

    public NotificationManagerReactiveImpl(AccountReactiveService accountService, EmailReactiveService emailService) {
        this.accountService = accountService;
        this.emailService = emailService;
    }

    @Override
    public void sendDiscountEmail(Long accountId) {
        executor.execute(() -> {
            Map<String, String> params = new HashMap<>();
            Mono<Account> account = accountService.findAccountById(accountId);
           // Mono<String> result = emailService.sendEmail(account, 18, params);
            //result.subscribe(messageId -> System.out.println("Email sent with ID:" + messageId));
        });
    }

    public void demo(Long accountId) {
        Map<String, String> params = new HashMap<>();
        accountService.findAccountById(accountId)
                .map(account -> emailService.sendEmail(account, 18, params))
                .subscribe(messageId -> System.out.println("Email sent with ID:" + messageId));

       // Mono<String> result = emailService.sendEmail(account, 18, params);
       // result.subscribe(messageId -> System.out.println("Email sent with ID:" + messageId));
    }
}
