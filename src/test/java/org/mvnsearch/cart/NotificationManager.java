package org.mvnsearch.cart;

/**
 * shopping cart notification manager
 *
 * @author leijuan
 */
public interface NotificationManager {

    void sendDiscountEmail(Long accountId);
}
