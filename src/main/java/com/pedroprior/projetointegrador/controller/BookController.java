package com.pedroprior.projetointegrador.controller;

import com.pedroprior.projetointegrador.entities.Book;


import com.pedroprior.projetointegrador.entities.Category;
import com.pedroprior.projetointegrador.repository.BookRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/book")
public class BookController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

    @Autowired
    BookRepository bookRepository;


    @PostMapping
    ResponseEntity addBook(@RequestBody Book book) {

        try {
            bookRepository.save(book);
            LOGGER.info("Livro salvo com sucesso");
            return ResponseEntity.ok().body(book);
        } catch(RuntimeException e) {
            LOGGER.error("Erro ao salvar", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }

    @GetMapping
    ResponseEntity<List<Book>> listBooks() {

        try {

            List<Book> books = new ArrayList<>();
            Iterable<Book> booksAll = bookRepository.findAll();
            for (Book book : booksAll) {
                books.add(book);
            }
            LOGGER.info("Sucesso ao realizar busca.");
            return ResponseEntity.ok().body(books);

        } catch(RuntimeException e) {
            LOGGER.error("Erro ao realizar busca.");
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    ResponseEntity getById(@PathVariable(value = "id") UUID id) {

        try {

            Book book = bookRepository.findById(id).get();
            LOGGER.info("Busca realizada com sucesso");
            return ResponseEntity.ok().body(book);

        } catch(RuntimeException e) {
            LOGGER.error("Error ao buscar livro.");
            throw new RuntimeException(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}")
    ResponseEntity<Book> alterBook(@PathVariable(value = "id") UUID id, @RequestBody Book book) {

        try {

            Book bookUpdate = bookRepository.findById(id).orElseThrow(() ->
                    new RuntimeException("Error" + id));

            bookUpdate.setName(book.getName());
            bookUpdate.setAuthor(book.getAuthor());
            bookUpdate.setIsbn(book.getIsbn());
            bookUpdate.setCategory(book.getCategory());
            bookUpdate.setDescription(book.getDescription());


            bookRepository.save(bookUpdate);

            LOGGER.info("Livro alterado com sucesso");
            return ResponseEntity.ok().body(bookUpdate);

        } catch (RuntimeException e) {
            LOGGER.error("Problema em alterar o livro");
            throw new RuntimeException(e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteBook(@PathVariable(value = "id") UUID id) {

        try {

            bookRepository.deleteById(id);
            LOGGER.info("Livro deletado com sucesso.");
            return ResponseEntity.ok().body("Usuário deletado");

        } catch(RuntimeException e) {
            LOGGER.error("Erro ao deletar o livro");
            throw new RuntimeException(e.getMessage());
        }
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
