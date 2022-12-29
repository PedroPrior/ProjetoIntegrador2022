package com.pedroprior.projetointegrador.controller;


import com.pedroprior.projetointegrador.entities.Author;
import com.pedroprior.projetointegrador.entities.Category;

import com.pedroprior.projetointegrador.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;


    @PostMapping
    ResponseEntity<String> addCategory(@RequestBody Category category) {
        categoryRepository.save(category);
        String message = "A categoria foi adicionar com sucesso " + category;
        return ResponseEntity.ok().body(message);
    }



    @GetMapping
    List listCategory() {
        return categoryRepository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<Object> getById(@PathVariable(value = "id") UUID id) {

        Optional<Category> category = categoryRepository.findById(id);

        if (!category.isPresent()) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado, tente novamente.");

        }
            return ResponseEntity.ok().body(category);


    }


    @PutMapping(value = "/{id}")
    ResponseEntity<Category> alterCategory(@PathVariable(value = "id") UUID id, @RequestBody Category category) {


        Category categoryUpdate = categoryRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Error" + id));

        categoryUpdate.setName(category.getName());
        categoryUpdate.setBooks(category.getBooks());
        categoryRepository.save(categoryUpdate);

        return ResponseEntity.ok().body(categoryUpdate);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteCategory(@PathVariable(value = "id") UUID id) {
        categoryRepository.deleteById(id);
        String message = "A categoria foi deletado com sucesso: " + id;
        return ResponseEntity.ok().body(message);
    }



    @RequestMapping("/list")
    public String listAuthor(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());


        return "auth/admin/admin-list-category";
    }


    @GetMapping("/new")
    public String addAuthorModel(Model model) {
        model.addAttribute("category", new Category());
        return "/create-category";
    }

    @PostMapping("/save")
    public String saveAuthor(@Validated Category category, BindingResult result,
                             RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "/create-category";
        }
        categoryRepository.save(category);
        attributes.addFlashAttribute("message", "Category is saved!");
        return "redirect:/category/new";
    }
}
