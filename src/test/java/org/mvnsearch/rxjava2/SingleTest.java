package org.mvnsearch.rxjava2;

import io.reactivex.Maybe;
import io.reactivex.Single;
import org.junit.jupiter.api.Test;

/**
 * Single Test
 *
 * @author linux_china
 */
@SuppressWarnings("ResultOfMethodCallIgnored")
public class SingleTest {

    @Test
    public void testSingle() {
        Single.just(1).subscribe(System.out::println);
    }

    @Test
    public void testMaybe() {
        Single<Boolean> single = Maybe.just(null).isEmpty();
        Maybe.just(2).subscribe(System.out::println);
    }

}
