package org.mvnsearch.cart;

import java.util.Map;

/**
 * email service
 *
 * @author linux_china
 */
public interface EmailService {
    
    String sendEmail(Account account, Integer templateId, Map<String, String> params);
}
