package org.mvnsearch.spring.reactor;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jakewharton.retrofit2.adapter.reactor.ReactorCallAdapterFactory;
import okhttp3.OkHttpClient;
import org.junit.Test;
import reactor.core.publisher.Mono;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * retrofit test
 *
 * @author linux_china
 */
public class RetrofitTest {

    @Test
    public void testRetrofitMono() throws Exception {
        HttpBinAPI httpBinAPI = httpBinServiceRetrofitAPI();
        Mono<HttpBinResponse> ip = httpBinAPI.ip();
        ip.subscribe(httpBinResponse -> System.out.print(httpBinResponse.getIp()));
        Thread.sleep(1000);
    }

    private HttpBinAPI httpBinServiceRetrofitAPI() {
        return new Retrofit.Builder().
                baseUrl("http://httpbin.org").
                client(okHttpClient()).
                addCallAdapterFactory(ReactorCallAdapterFactory.create()).
                addConverterFactory(JacksonConverterFactory.create(objectMapper())).
                build().
                create(HttpBinAPI.class);
    }

    private OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder().build();
    }

    private ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }
}
