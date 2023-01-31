package org.mvnsearch.rsocket.responder;

import io.rsocket.core.RSocketServer;
import io.rsocket.transport.netty.server.TcpServerTransport;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Mono;


/**
 * RSocket responder
 *
 * @author linux_china
 */
@SpringBootApplication
public class RsocketAppServer {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(RsocketAppServer.class, args);
    }

    @PostConstruct
    public void init() {
        System.out.println("Starting the RSocket Server");
        RSocketServer.create()
                .acceptor((setup, sendingSocket) -> Mono.just(new RSocketResponderHandler(setup, sendingSocket)))
                .bind(TcpServerTransport.create("localhost", 42252))
                .subscribe();
    }
}
