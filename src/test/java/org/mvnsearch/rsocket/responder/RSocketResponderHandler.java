package org.mvnsearch.rsocket.responder;

import io.rsocket.ConnectionSetupPayload;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import reactor.core.publisher.Mono;

/**
 * RSocket responder handler
 *
 * @author linux_china
 */
public class RSocketResponderHandler implements RSocket {

    public RSocketResponderHandler(ConnectionSetupPayload setup, RSocket sendingSocket) {

    }

    @Override
    public Mono<Payload> requestResponse(Payload payload) {
        return Mono.just(payload);
    }
}
