package org.mvnsearch.rsocket.requester;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.util.DefaultPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * rsocket app runner
 *
 * @author linux_china
 */
@Component
public class RSocketAppRunner1 implements ApplicationRunner {
    @Autowired
    private RSocket rSocket;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        rSocket.requestResponse(DefaultPayload.create("Hello"))
                .map(Payload::getDataUtf8)
                .onErrorReturn("error")
                .subscribe(System.out::println);
    }
}
