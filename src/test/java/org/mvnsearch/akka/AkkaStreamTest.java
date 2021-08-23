package org.mvnsearch.akka;

import akka.Done;
import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletionStage;

/**
 * Akka stream test
 *
 * @author linux_china
 */
public class AkkaStreamTest {
    private static ActorSystem system;
    private static Materializer materializer;

    @BeforeAll
    public static void setUp() {
        system = ActorSystem.create("QuickStart");
        materializer = ActorMaterializer.create(system);
    }

    @AfterAll
    public static void tearDown() {
        system.terminate();
    }

    @Test
    public void testDemo() throws Exception {
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
        single.runForeach(t -> {
            System.out.println(t);
        }, materializer);
        Thread.sleep(1000);
    }

    @Test
    public void testSink() throws Exception {
        Sink<String, CompletionStage<Done>> print = Sink.foreach(t -> {
            System.out.println(t);
        });
        Source<String, NotUsed> single = Source.single("A");
        single.runWith(print, materializer);
        Thread.sleep(1000);
    }

    @Test
    public void testFlow() throws Exception {
        Flow<String, String, NotUsed> flowPrototype = Flow.create();
        Flow<String, String, NotUsed> flow = flowPrototype.map(t -> {
            return t + ":";
        });
        Source.single("A").via(flow).runForeach(t -> {
            System.out.println(t);
        }, materializer);
        Thread.sleep(1000);


    }
}
