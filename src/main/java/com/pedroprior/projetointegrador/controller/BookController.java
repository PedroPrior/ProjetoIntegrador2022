package com.pedroprior.projetointegrador.controller;

import com.pedroprior.projetointegrador.entities.Author;
import com.pedroprior.projetointegrador.entities.Book;


import com.pedroprior.projetointegrador.entities.Category;
import com.pedroprior.projetointegrador.repository.BookRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.UUID;

@Controller
@RequestMapping("/book")
public class BookController {



    @Autowired
    BookRepository bookRepository;

    @GetMapping("/{id}")
    ResponseEntity getById(@PathVariable(value = "id") UUID id) {

        try {

            Book book = bookRepository.findById(id).get();

            return ResponseEntity.ok().body(book);

        } catch(RuntimeException e) {

            throw new RuntimeException(e.getMessage());
        }
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER')")
    @RequestMapping("/list")
    public String listAuthor(Model model) {
        model.addAttribute("books", bookRepository.findAll());

        return "auth/admin/admin-list-book";
    }

    @GetMapping("/new")
    public String addBookModel(Model model) {
        model.addAttribute("book", new Book());
        return "/create-book";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @PostMapping("/save")
    public String saveBook(@Validated Book book, BindingResult result,
                             RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "/create-book";
        }
        bookRepository.save(book);
        attributes.addFlashAttribute("message", "Book is saved!");
        return "redirect:/book/new";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable(value = "id") UUID id, @Valid Book book,
                               BindingResult bindingResult, Model model
    ) {

        if (bindingResult.hasErrors()) {
            book.setId(id);
            return "update-book";
        }

        bookRepository.save(book);

        return "redirect:/book/list";
    }



    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") UUID id, Model model) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("book", book);
        return "/update-book";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") UUID id, Model model) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        bookRepository.delete(book);
        return "redirect:/book/list";

    }


}
