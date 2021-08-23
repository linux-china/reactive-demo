package org.mvnsearch.cart;

import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * email service
 *
 * @author linux_china
 */
public interface EmailReactiveService {
    
    Mono<String> sendEmail(Account account, Integer templateId, Map<String, String> params);
}
