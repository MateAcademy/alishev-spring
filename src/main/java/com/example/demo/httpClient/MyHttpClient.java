package com.example.demo.httpClient;

import com.example.demo.dto.UserRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange(url = "api")
public interface MyHttpClient {

    @GetExchange("hello")
    String getHelloMessage(@RequestParam(required = false) String name,
                           @RequestParam(required = false) Integer age);


    @PostExchange("helloPost")
    String postHelloMessage(@RequestParam(required = false) String name,
                            @RequestParam(required = false) Integer age);

    //  позволяет делать запрос с переменными в URL:  http://localhost:8080/api/descriptionGet/5
    //  @PathVariable используется для извлечения переменных из URL-пути (path) запроса.
    //  @PathVariable следует использовать, когда переменная является частью URL-пути.
    @GetExchange("descriptionGet/{id}")
    String getDescriptionMessage(@PathVariable("id") String id,   // id в самом запросе
                                 @RequestParam(required = false) String name,  // параметры в запросе
                                 @RequestParam(required = false) Integer age);

    @PostExchange("helloPostWithBody")
    void postHelloMessageWithBody(@RequestBody UserRequest request);

    @GetExchange("slow")
    String getSlowMessage();
}