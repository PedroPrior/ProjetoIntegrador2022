package com.pedroprior.projetointegrador.controller;


import com.pedroprior.projetointegrador.entities.Author;
import com.pedroprior.projetointegrador.entities.Category;

import com.pedroprior.projetointegrador.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    @PostMapping
    ResponseEntity<String> addCategory(@RequestBody Category category) {
        try {
            categoryRepository.save(category);
            LOGGER.info("Categoria criada com sucesso " + category.getId());
            return ResponseEntity.ok().body("Categoria salva com sucesso.");

        } catch(RuntimeException e) {
            LOGGER.error("Erro ao criar categoria", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }



    @GetMapping
    List listCategory() {
        try {
            LOGGER.info("Sucesso ao realizar a busca de categorias");
            return categoryRepository.findAll();
        } catch(RuntimeException e) {
            LOGGER.error("Erro ao listar as categorias", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }


    }

    @GetMapping("/{id}")
    ResponseEntity<Object> getById(@PathVariable(value = "id") UUID id) {

        try {

            Optional<Category> category = categoryRepository.findById(id);

            LOGGER.info("Sucesso em realizar a busca da categoria");
            return ResponseEntity.ok().body(category);

        } catch (RuntimeException e) {
            LOGGER.error("Erro ao buscar categoria");
            throw new RuntimeException(e.getMessage());
        }


    }


    @PutMapping(value = "/{id}")
    ResponseEntity<Category> alterCategory(@PathVariable(value = "id") UUID id, @RequestBody Category category) {

        try {
            Category categoryUpdate = categoryRepository.findById(id).orElseThrow(() ->
                    new RuntimeException("Error" + id));

            categoryUpdate.setName(category.getName());
            categoryUpdate.setBooks(category.getBooks());
            categoryRepository.save(categoryUpdate);

            LOGGER.info("Sucesso ao alterar a categoria.");
            return ResponseEntity.ok().body(categoryUpdate);

        } catch(RuntimeException e) {
            LOGGER.error("Erro ao alterar a categoria.");
            throw new RuntimeException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")


    ResponseEntity<String> deleteCategory(@PathVariable(value = "id") UUID id) {

        try {
            categoryRepository.deleteById(id);
            LOGGER.info("Categoria de ID: " + id + " deletada com sucesso");
            return ResponseEntity.ok().body("Categoria de ID: " + id + " deletada com sucesso");

        } catch (RuntimeException e) {
            LOGGER.error("Erro ao deletar categoria.");
            throw new RuntimeException(e.getMessage());
        }

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
