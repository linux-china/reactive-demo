package org.mvnsearch.spring.reactor;

import reactor.core.publisher.Mono;
import retrofit2.http.GET;

/**
 * httpbin api
 *
 * @author linux_china
 */
public interface HttpBinAPI {
    @GET("/ip")
    Mono<HttpBinResponse> ip();

    @GET("/uuid")
    Mono<HttpBinResponse> uuid();

    @GET("/headers")
    Mono<HttpBinResponse> headers();
}
