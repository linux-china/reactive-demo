package org.mvnsearch.reactor.netty;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import reactor.core.Exceptions;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.netty.tcp.TcpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Netty TCP client test
 *
 * @author linux_china
 */
public class NettyClientTest {
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testConnect() throws Exception {
        Connection client = TcpClient.create().host("127.0.0.1").port(1234)
                .handle((in, out) -> { //in
                    in.receive()
                            .asByteArray()
                            .log("receive")
                            .subscribe(data -> {
                                try {
                                    User user = objectMapper.readValue(data, User.class);
                                    System.out.println("Nick:" + user.getNick());
                                } catch (Exception e) {

                                }
                            });

                    //out
                    return out.send(Mono.just(new User(1, "linux_china"))
                            .map(s -> {
                                try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
                                    objectMapper.writeValue(os, s);
                                    return out.alloc()
                                            .buffer()
                                            .writeBytes(os.toByteArray());
                                } catch (IOException ioe) {
                                    throw Exceptions.propagate(ioe);
                                }
                            })).neverComplete();
                })
                .wiretap(true)
                .connectNow();
        Thread.sleep(2000);
        client.dispose();
    }
}
