package org.mvnsearch.reactor.netty;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.reactivestreams.Publisher;
import reactor.core.Exceptions;
import reactor.core.publisher.Mono;
import reactor.netty.NettyInbound;
import reactor.netty.NettyOutbound;
import reactor.netty.tcp.TcpServer;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.function.BiFunction;

/**
 * Netty Reactor Tcp Server
 *
 * @author linux_china
 */
public class TcpServerApp {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        TcpServer.create()
                .handle(new BiFunction<NettyInbound, NettyOutbound, Publisher<Void>>() {
                    @Override
                    public Publisher<Void> apply(NettyInbound in, NettyOutbound out) {
                        in.receive()
                                .asByteArray()
                                .map(bb -> {
                                    try {
                                        return objectMapper.readValue(bb, User.class);
                                    } catch (IOException io) {
                                        throw Exceptions.propagate(io);
                                    }
                                })
                                .log("conn")
                                .subscribe(data -> {
                                    if ("Kill".equals(data.getNick())) {
                                        latch.countDown();
                                    }
                                    System.out.println("Nick:" + data.getNick());
                                });

                        try {
                            return out.sendByteArray(Mono.just(objectMapper.writeValueAsBytes(new User(1, "Jackie")))).neverComplete();
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        return Mono.empty();
                    }
                })
                .host("127.0.0.1")
                .port(1234)
                .bind()
                .block();
        latch.await();
    }
}
