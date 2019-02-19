package org.mvnsearch.rsocket.requester;

import io.rsocket.*;
import io.rsocket.transport.local.LocalServerTransport;
import io.rsocket.uri.UriTransportRegistry;
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
        localServer = RSocketFactory.receive()
                .acceptor(new SocketAcceptor() {
                    @Override
                    public Mono<RSocket> accept(ConnectionSetupPayload setup, RSocket sendingSocket) {
                        return Mono.just(new AbstractRSocket() {
                            @Override
                            public Mono<Payload> requestResponse(Payload payload) {
                                return Mono.just(payload);
                            }
                        });
                    }
                })
                .transport(LocalServerTransport.create("test-local-server"))
                .start()
                .block();
        //rsocket
        rSocket =
                RSocketFactory.connect()
                        .transport(UriTransportRegistry.clientForUri("local:test-local-server"))
                        .start()
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
