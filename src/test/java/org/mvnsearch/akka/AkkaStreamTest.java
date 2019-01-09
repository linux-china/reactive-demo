package org.mvnsearch.akka;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.Source;
import org.junit.Test;

/**
 * Akka stream test
 *
 * @author linux_china
 */
public class AkkaStreamTest {
    @Test
    public void testDemo() throws Exception {
        final ActorSystem system = ActorSystem.create("QuickStart");
        final Materializer materializer = ActorMaterializer.create(system);

        final Source<Integer, NotUsed> source = Source.range(1, 10);
        source.map(t -> {
            return t + 1;
        }).runForeach(i -> {
            System.out.println(i);
        }, materializer);

        Thread.sleep(1000);
    }

    @Test
    public void testSingle() throws Exception {
        Source<String, NotUsed> single = Source.single("A");
        final ActorSystem system = ActorSystem.create("QuickStart");
        final Materializer materializer = ActorMaterializer.create(system);
        single.runForeach(t -> {
            System.out.println(t);
        }, materializer);
        Thread.sleep(1000);
    }
}
