package com.example.demo.config;

import com.example.demo.httpClient.MyHttpHello;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class WebHelloConfig {

    @Bean
    public WebClient myWebHello() {
        return WebClient.builder()
            .baseUrl("http://localhost:8086")
            .clientConnector(new ReactorClientHttpConnector(
                HttpClient.create()
                    .responseTimeout(Duration.ofSeconds(5))
            ))
            .build();
    }

    @Bean
    public MyHttpHello myHttpHello(WebClient myWebHello) {
        return HttpServiceProxyFactory
            .builderFor(WebClientAdapter.create(myWebHello))
            .build()
            .createClient(MyHttpHello.class);
    }
}