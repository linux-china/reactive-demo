package org.mvnsearch.adapter;

/**
 * Created with IntelliJ IDEA.
 *
 * @author linux_china
 */
public class Transaction {
    private Long id;

    public Transaction(Long id) {
        this.id = id;
    }

    public double total() {
        return 1.0;
    }
}
