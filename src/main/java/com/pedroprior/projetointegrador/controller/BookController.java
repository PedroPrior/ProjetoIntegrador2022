package com.pedroprior.projetointegrador.controller;

import com.pedroprior.projetointegrador.entities.Book;


import com.pedroprior.projetointegrador.entities.Category;
import com.pedroprior.projetointegrador.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookRepository bookRepository;


    @PostMapping
    ResponseEntity addBook(@RequestBody Book book) {
        bookRepository.save(book);
        return ResponseEntity.ok().body(book);
    }

    @GetMapping
    ResponseEntity<List<Book>> listBooks() {
        List<Book> books = new ArrayList<>();
        Iterable<Book> booksAll = bookRepository.findAll();
        for (Book book : booksAll) {
            books.add(book);

        }

        return ResponseEntity.ok().body(books);
    }

    @GetMapping("/{id}")
    ResponseEntity getById(@PathVariable(value = "id") UUID id) {

        Book book = bookRepository.findById(id).get();
        return ResponseEntity.ok().body(book);
    }

    @PutMapping(value = "/{id}")
    ResponseEntity<Book> alterbook(@PathVariable(value = "id") UUID id, @RequestBody Book book) {


        Book bookUpdate = bookRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Error" + id));

        bookUpdate.setName(book.getName());
        bookUpdate.setAuthor(book.getAuthor());
        bookUpdate.setIsbn(book.getIsbn());
        bookUpdate.setCategory(book.getCategory());
        bookUpdate.setDescription(book.getDescription());


        bookRepository.save(bookUpdate);

        return ResponseEntity.ok().body(bookUpdate);
    }


    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteBook(@PathVariable(value = "id") UUID id) {
        bookRepository.deleteById(id);
        String message = "Usuário deletado com sucesso " + id;
        return ResponseEntity.ok().body(message);
    }

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
}
