package com.example.demo.httpClient;


import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

// Это аннотация в Spring6 и springboot3, которая используется для декларативного создания HTTP клиентов.
// Она позволяет описывать REST запросы в интерфейсе
// Преимущества @HttpExchange:
// позволяет автоматически создавать REST клиенты через spring
@HttpExchange(url = "hello")  // @HttpExchange(url = "hello") указывает базовый url
public interface MyHttpHello {

    @GetExchange("say-hello")
    String getHelloMessage(@RequestParam(required = false) String name,
                           @RequestParam(required = false) Integer age);
}
