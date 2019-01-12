package org.mvnsearch.kafka_streams;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class KafkaStreamsDemo {

    public static void main(String[] args) throws Exception {
        final StreamsBuilder builder = new StreamsBuilder();
        buildTopology(builder);
        final KafkaStreams streams = new KafkaStreams(builder.build(), kafkaProps());
        final CountDownLatch latch = new CountDownLatch(1);
        // attach shutdown handler to catch control-c
        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
            @Override
            public void run() {
                streams.close();
                latch.countDown();
            }
        });
        try {
            streams.start();
            latch.await();
        } catch (Throwable e) {
            System.exit(1);
        }
        System.exit(0);
    }


    private static void buildTopology(StreamsBuilder builder) {
        KStream<Object, Object> testTopic = builder.stream("testTopic");
        testTopic.foreach((key, value) -> {
            System.out.println(value);
        });
    }

    private static Properties kafkaProps() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-demo");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        return props;
    }
}