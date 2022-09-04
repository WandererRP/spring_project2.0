package com.rol.project2.controllers;


import com.rol.project2.services.BooksService;
import com.rol.project2.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.rol.project2.models.Person;

import javax.validation.Valid;


@Validated
@Controller
@RequestMapping("/people")
public class PeopleController {


    private final PeopleService peopleService;
    private final BooksService booksService;



    @Autowired
    public PeopleController(PeopleService peopleService, BooksService booksService) {
        this.peopleService = peopleService;
        this.booksService = booksService;
    }


    @GetMapping()
    public String index(Model model){

        model.addAttribute("people", peopleService.findAll());
        return "people/index";
    }

    @GetMapping("/{person_id}")
    public String show(@PathVariable("person_id") int person_id, Model model){

        Person person = peopleService.findOne(person_id);
        model.addAttribute("person", person);
        model.addAttribute("books", booksService.findByOwner(person));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("person", new Person());
        return "people/new";
    }


    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){


        if(bindingResult.hasErrors())
            return "people/new";

        peopleService.save(person);
        return "redirect:/people";

    }

    @GetMapping("/{person_id}/edit")
    public String edit(Model model, @PathVariable("person_id") int person_id){
        model.addAttribute("person", peopleService.findOne(person_id));
        return "people/edit";
    }

    @PatchMapping("/{person_id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult, @PathVariable("person_id") int person_id){

        if(bindingResult.hasErrors())
            return "people/edit";

        peopleService.update(person_id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{person_id}")
    public String delete(@PathVariable("person_id") int id){

        peopleService.delete(id);

        return "redirect:/people";

    }





}