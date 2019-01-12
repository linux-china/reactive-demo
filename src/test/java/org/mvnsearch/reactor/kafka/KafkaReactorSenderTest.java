package org.mvnsearch.reactor.kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.BeforeClass;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderRecord;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * kafka reactor sender test
 *
 * @author linux_china
 */
public class KafkaReactorSenderTest {
    private static KafkaSender<String, String> sender;

    @BeforeClass
    public static void setUp() {
        sender = KafkaSender.create(SenderOptions.<String, String>create(producerProps()).maxInFlight(1024));
    }

    public Flux<String> outboundFlux() {
        return Flux.just("first", "second");
    }

    @Test
    public void testSend() {
        //no metadata
        //sender.createOutbound().send(outboundFlux().map(text -> new ProducerRecord<>("testTopic", UUID.randomUUID().toString(), text))).then().subscribe();
        //with metadata
        sender.send(outboundFlux().map(text -> SenderRecord.create("testTopic", 1, System.currentTimeMillis(), UUID.randomUUID().toString(), text, null))).subscribe();
        //close sender
        //sender.close();
    }


    public static Map<String, Object> producerProps() {
        Map<String, Object> producerProps = new HashMap<>();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return producerProps;
    }


}
