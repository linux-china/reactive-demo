package org.mvnsearch.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.retry.Repeat;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Exception test for doOnError, onErrorMap, onErrorReturn, and onErrorResume
 *
 * @author linux_china
 */
public class ExceptionTest {

    @Test
    public void testOnErrorMap() throws Exception {
        Mono.just("good").map(this::exceptionCall).doOnError(error -> {
            System.out.println(error.getMessage());
        }).onErrorMap(EncodingException.class, error -> {
            System.out.println("map exception");
            return new Exception("cattcched exception");
        }).subscribe(text -> {
            System.out.println("subsribed: " + text);
        });
        Thread.sleep(1000);
    }

    @Test
    public void testEmpty() {
        AtomicInteger atomicInteger = new AtomicInteger(1);
        Mono result = Mono.just(0)
                .map(num -> {
                    System.out.println("map: " + atomicInteger.get());
                    return num + 1;
                })
                .flatMap(text -> {
                    System.out.println("flatMap: ");
                    if (atomicInteger.incrementAndGet() <= 3) {
                        return Mono.empty();
                    }
                    return Mono.just(atomicInteger.get());
                }).repeatWhenEmpty(Repeat.times(5));
        System.out.println(result.block());
    }

    public String exceptionCall(String text) throws EncodingException {
        throw new EncodingException("error to encode");
    }
}
