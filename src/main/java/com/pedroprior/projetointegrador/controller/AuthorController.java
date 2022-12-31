package com.pedroprior.projetointegrador.controller;


import com.pedroprior.projetointegrador.entities.Author;

import com.pedroprior.projetointegrador.repository.AuthorRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorController.class);

    @Autowired
    AuthorRepository authorRepository;

    @PostMapping
    ResponseEntity<?> addAuthor(@RequestBody  Author author) {

        authorRepository.save(author);
        LOGGER.info("Autor criado com sucesso", author.toString());
        return ResponseEntity.ok().body(author);

    }


    @GetMapping(value = "/authors")
    ResponseEntity<List<Author>> listAuthors() {

        try {
            List<Author> authors = authorRepository.findAll();
            LOGGER.info("Busca realizada com sucesso");
            return ResponseEntity.ok().body(authors);

        } catch(RuntimeException e) {
            LOGGER.error("Erro ao realizar busca.", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }

    @GetMapping("/{id}")
    ResponseEntity<?> getById(@PathVariable(value = "id") UUID id) {

        try {

            Author author = authorRepository.findById(id).get();
            LOGGER.info("Autor encontrado com sucesso!");
            return ResponseEntity.ok().body(author);

        } catch(RuntimeException e) {
            LOGGER.error("Autor não encontrado!");
            throw new RuntimeException(e.getMessage());
        }

    }


    @PutMapping(value = "/{id}")
    ResponseEntity<Author> alterAuthor(@PathVariable(value = "id") UUID id, @RequestBody Author author) {



        try {

            Author authorUpdate = authorRepository.findById(id).orElseThrow(() ->
                    new RuntimeException("Error" + id));

            authorUpdate.setName(author.getName());
            authorUpdate.setBooks(author.getBooks());
            authorUpdate.setBirthDate(author.getBirthDate());


            authorRepository.save(authorUpdate);


            LOGGER.info("Update realizado com sucesso");
            return ResponseEntity.ok().body(authorUpdate);

        } catch(RuntimeException e) {
            LOGGER.error("Erro ao realizar o update.", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }


    }


    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteAuthor(@PathVariable(value = "id") UUID id) {

        try {
            authorRepository.deleteById(id);
            LOGGER.info("Autor deletado com sucesso.");
            return ResponseEntity.ok().body("Ok, usuário deletado!");
        } catch(RuntimeException e ) {
            LOGGER.error("Autor não encotrado.");
            throw new RuntimeException(e.getMessage());
        }


    }


    // Thymeleaf <--

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
