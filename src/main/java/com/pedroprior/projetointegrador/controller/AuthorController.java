package com.pedroprior.projetointegrador.controller;


import com.pedroprior.projetointegrador.entities.Author;

import com.pedroprior.projetointegrador.repository.AuthorRepository;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    AuthorRepository authorRepository;

    @PostMapping
    ResponseEntity<?> addAuthor(@RequestBody  Author author) {

        authorRepository.save(author);
        return ResponseEntity.ok().body("Ok, usuário adicionar com sucesso");

    }


    @GetMapping(value = "/authors")
    ResponseEntity<List<Author>> listAuthors() {

        List<Author> authors = authorRepository.findAll();


       return ResponseEntity.ok().body(authors);

    }

    @GetMapping("/{id}")
    ResponseEntity<?> getById(@PathVariable(value = "id") UUID id) {

        Author author = authorRepository.findById(id).get();

        return ResponseEntity.ok().body(author);
    }


    @PutMapping(value = "/{id}")
    ResponseEntity<Author> alterAuthor(@PathVariable(value = "id") UUID id, @RequestBody Author author) {


           Author authorUpdate = authorRepository.findById(id).orElseThrow(() ->
                   new RuntimeException("Error" + id));

            authorUpdate.setName(author.getName());
            authorUpdate.setBooks(author.getBooks());
            authorUpdate.setBirthDate(author.getBirthDate());


            authorRepository.save(authorUpdate);



        return ResponseEntity.ok().body(authorUpdate);
    }


    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteAuthor(@PathVariable(value = "id") UUID id) {

        authorRepository.deleteById(id);

        return ResponseEntity.ok().body("Ok, usuário deletado!");
    }




    @GetMapping("/new")
    public String addAuthorModel(Model model) {
        model.addAttribute("author", new Author());
        return "/create-author";
    }


    @RequestMapping("/list")
    public String listAuthor(Model model) {
        model.addAttribute("authors", authorRepository.findAll());


        return "auth/admin/admin-list-author";
    }

    @PostMapping("/save")
    public String saveAuthor(@Validated Author author, BindingResult result,
                                RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "/create-author";
        }
        authorRepository.save(author);
        attributes.addFlashAttribute("message", "Author is saved!");
        return "redirect:/author/new";
    }
}
