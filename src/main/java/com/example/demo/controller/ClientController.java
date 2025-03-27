package com.example.demo.controller;

import com.example.demo.dto.UserRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class ClientController {

    @GetMapping("hello")
    public String helloGet(@RequestParam(required = false) String name, @RequestParam(required = false) Integer age) {
        return "@RequestParam: helloGet from Spring Boot! name: " + name + ", age: " + age;
    }

    @PostMapping("helloPost")
    public String helloPost(@RequestParam(required = false) String name, @RequestParam(required = false) Integer age) {
        return "@RequestParam: helloPost from Spring Boot! name: " + name + ", age: " + age;
    }

    @GetMapping("descriptionGet/{id}")
    public void descriptionGet(@PathVariable("id") int id,
                               @RequestParam(required = false) String name,
                               @RequestParam(required = false) Integer age) {
        System.out.println("@PathVariable: id =" + id + ", @RequestParam: name =" + name + ", age =" + age);
    }

    @PostMapping("helloPostWithBody")
    public void helloPostWithBody(@RequestBody UserRequest request) {
        System.out.println("@RequestBody: " + request.getName() + ", age: " + request.getAge());
    }

    @GetMapping("slow")
    public String slowResponse() throws InterruptedException {
        Thread.sleep(10_000);
        return "Delayed response";
    }
}