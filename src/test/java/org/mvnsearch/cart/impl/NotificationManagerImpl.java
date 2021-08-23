package org.mvnsearch.cart.impl;

import org.mvnsearch.cart.Account;
import org.mvnsearch.cart.AccountService;
import org.mvnsearch.cart.EmailService;
import org.mvnsearch.cart.NotificationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * notification manager
 *
 * @author linux_china
 */
@Component
public class NotificationManagerImpl implements NotificationManager {
    private Logger log = LoggerFactory.getLogger(NotificationManagerImpl.class);
    private AccountService accountService;
    private EmailService emailService;
    private ExecutorService executor =  new ThreadPoolExecutor(8, 8, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(200));

    public NotificationManagerImpl(AccountService accountService, EmailService emailService) {
        this.accountService = accountService;
        this.emailService = emailService;
    }

    @Override
    public void sendDiscountEmail(Long accountId) {
        executor.execute(() -> {
            Account account = accountService.findAccountById(accountId);
            if (account != null) {
                Map<String, String> params = new HashMap<>();
                //todo 填充必要email模板需要的参数
                String messageId = emailService.sendEmail(account, 18, params);
                //todo 将message id保存一下，方便后续查找邮件是否发出
                log.info("Email sent with ID:" + messageId);
            }
        });
    }
}
