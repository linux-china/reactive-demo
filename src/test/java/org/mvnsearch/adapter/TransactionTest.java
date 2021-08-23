package org.mvnsearch.adapter;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.math.MathFlux;

/**
 * Created with IntelliJ IDEA.
 *
 * @author linux_china
 */
public class TransactionTest {

    @Test
    public void testSum() throws Exception {
        TransactionService transactionService = new TransactionService();
        Long[] transactionIds = new Long[]{111L, 222L, 333L};
        Flux.fromArray(transactionIds)
                .flatMap(transactionService::findById)
                .window(2)
                .flatMap(transactions -> MathFlux.sumDouble(transactions.map(Transaction::total)))
                .reduce(0.0, (x1, x2) -> x1 + x2)
                .subscribe(System.out::println);

        Thread.sleep(1000);

    }
}
