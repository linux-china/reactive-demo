package org.mvnsearch.reactor.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * kafka receiver test
 *
 * @author linux_china
 */
public class KafkaReactorReceiverTest {
    private static Flux<ReceiverRecord<String, String>> inboundFlux;

    @BeforeClass
    public static void setUp() {
        inboundFlux = KafkaReceiver.create(ReceiverOptions.<String, String>create(consumerProps())
                .subscription(Collections.singleton("testTopic")))
                .receive();
    }

    @AfterClass
    public static void tearDown() {
    }

    @Test
    public void testConsumer() {
        inboundFlux.subscribe(t -> {
            System.out.println(t);
        });
    }

    private static Map<String, Object> consumerProps() {
        Map<String, Object> consumerProps = new HashMap<>();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "sample-group");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return consumerProps;
    }


}
