package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("hello")
public class HelloController {

    // http://localhost:8080/hello/sayHello?name=Serhii&age=10
    @GetMapping("say-hello")
    public String sayHello(@RequestParam(required = false) String name,
                           @RequestParam(required = false) Integer age,
                           Model model) {
        System.out.println("name: " +  name + " age: " + age );

        model.addAttribute("name", name);
        model.addAttribute("age", age);
        return "sayHello";
    }

}
