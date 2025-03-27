package com.example.demo.controller;

import com.example.demo.dto.PersonResponse;
import com.example.demo.models.Person;
import com.example.demo.service.PeopleService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("people")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PeopleController {

    final PeopleService peopleService;

    @GetMapping
    public String index(Model model) {
        List<PersonResponse> allPeople = peopleService.getAllPeople();
        model.addAttribute("people", allPeople);
        return "people/all-people";
    }

    @GetMapping("{id}")   // ("{id}") позволяет делать запрос с переменными в URL и это число поместится в аргументы метода
    public String show(@PathVariable Integer id, Model model) {
        model.addAttribute("person", peopleService.getPersonById(id));
        return "people/person-details";
    }

    @GetMapping("new")
    public String newPerson(@ModelAttribute Person person) {
        return "people/new-person";
    }

    @PostMapping
    public String create(@ModelAttribute @Valid Person person,
                         BindingResult bindingResult) {  // @RequestParam(required = false) String name
        if (bindingResult.hasErrors()) {
            return "people/new-person";
        }
        peopleService.save(person);
        return "people/success-page";
    }

    @GetMapping("{id}/edit")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("person", peopleService.getPersonById(id));
        return "people/edit-person";
    }

    @PatchMapping("{id}")
    public String update(@ModelAttribute @Valid Person person,
                         BindingResult bindingResult,
                         @PathVariable Integer id) {
        if (bindingResult.hasErrors()) {
            return "people/edit-person";
        }
        peopleService.update(id, person);
        return "people/success-page";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Integer id) {
        peopleService.delete(id);
        return "redirect:/people";
    }
}
