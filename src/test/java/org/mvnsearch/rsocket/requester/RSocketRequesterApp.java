package org.mvnsearch.rsocket.requester;

import io.rsocket.AbstractRSocket;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.client.TcpClientTransport;
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
        RSocket rSocket =
                RSocketFactory.connect()
                        .metadataMimeType("text/plain")
                        .dataMimeType("application/json")
                        .acceptor(peerRSocket -> new AbstractRSocket() {
                            @Override
                            public Mono<Payload> requestResponse(Payload payload) {
                                System.out.println("Received from responder " + payload.getDataUtf8());
                                return Mono.just(payload);
                            }
                        })
                        .transport(TcpClientTransport.create("localhost", 7000))
                        .start()
                        .onTerminateDetach()
                        .block();
        return rSocket;
    }


}
