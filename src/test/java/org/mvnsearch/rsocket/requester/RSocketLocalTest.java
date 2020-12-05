package org.mvnsearch.rsocket.requester;

import io.rsocket.*;
import io.rsocket.core.RSocketConnector;
import io.rsocket.core.RSocketServer;
import io.rsocket.transport.local.LocalClientTransport;
import io.rsocket.transport.local.LocalServerTransport;
import io.rsocket.util.DefaultPayload;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import reactor.core.publisher.Mono;

/**
 * RSocket local test
 *
 * @author linux_china
 */
public class RSocketLocalTest {
    private static RSocket rSocket;
    private static Closeable localServer;

    @BeforeClass
    public static void setUp() {
        //create a local test server
        localServer = RSocketServer.create()
                .acceptor(new SocketAcceptor() {
                    @Override
                    public Mono<RSocket> accept(ConnectionSetupPayload setup, RSocket sendingSocket) {
                        return Mono.just(new RSocket() {
                            @Override
                            public Mono<Payload> requestResponse(Payload payload) {
                                return Mono.just(payload);
                            }
                        });
                    }
                })
                .bind(LocalServerTransport.create("test-local-server"))
                .block();
        //rsocket
        rSocket = RSocketConnector.create()
                .connect(LocalClientTransport.create("local:test-local-server"))
                .onTerminateDetach()
                .block();
    }

    @AfterClass
    public static void tearDown() {
        rSocket.dispose();
        localServer.dispose();
    }

    @Test
    public void testRequest() throws Exception {
        rSocket.requestResponse(DefaultPayload.create("hello"))
                .subscribe();
        Thread.sleep(1000);
    }
}
