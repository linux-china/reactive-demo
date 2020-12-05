package org.mvnsearch.rsocket.requester;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.core.RSocketConnector;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

/**
 * RSocket requester
 *
 * @author linux_china
 */
@SpringBootApplication
public class RSocketRequesterApp {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(RSocketRequesterApp.class, args);
    }

    @Bean(destroyMethod = "dispose")
    public RSocket rSocket() {
        return RSocketConnector.create()
                .metadataMimeType("text/plain")
                .dataMimeType("application/json")
                .acceptor((setup, sendingSocket) -> {
                    return Mono.just(new RSocket() {
                        @NotNull
                        @Override
                        public Mono<Payload> requestResponse(@NotNull Payload payload) {
                            System.out.println("Received from responder " + payload.getDataUtf8());
                            return Mono.just(payload);
                        }
                    });
                })
                .connect(TcpClientTransport.create("localhost", 7000))
                .onTerminateDetach()
                .block();
    }


}
