package org.mvnsearch.rsocket;

import io.rsocket.AbstractRSocket;
import io.rsocket.Payload;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.server.TcpServerTransport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Mono;

/**
 * RSocket server
 *
 * @author linux_china
 */
@SpringBootApplication
public class RsocketServer {
    public static void main(String[] args) {
        SpringApplication.run(RsocketServer.class, args);
        init();
    }

    public static void init() {
        RSocketFactory.receive()
                .acceptor(
                        (setupPayload, reactiveSocket) ->
                                Mono.just(
                                        new AbstractRSocket() {
                                            @Override
                                            public Mono<Payload> requestResponse(Payload p) {
                                                return Mono.just(p);
                                            }
                                        }))
                .transport(TcpServerTransport.create("localhost", 7000))
                .start()
                .subscribe();
    }
}
