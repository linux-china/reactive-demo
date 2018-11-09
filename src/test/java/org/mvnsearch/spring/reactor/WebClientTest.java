package org.mvnsearch.spring.reactor;

import org.junit.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

/**
 * webclient test
 *
 * @author linux_china
 */
public class WebClientTest {

    @Test
    public void testDemo() throws Exception {
        WebClient webClient = WebClient.builder().build();
        webClient
                .get()
                .uri("http://httpbin.org/ip")
                .retrieve()
                .bodyToMono(Map.class).
                subscribe(result->System.out.println(result.get("origin")));
        Thread.sleep(1000);
    }
}
