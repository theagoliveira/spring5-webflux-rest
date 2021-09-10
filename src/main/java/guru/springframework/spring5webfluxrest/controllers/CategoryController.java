package guru.springframework.spring5webfluxrest.controllers;

import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.spring5webfluxrest.domain.Category;
import guru.springframework.spring5webfluxrest.repositories.CategoryRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = CategoryController.BASE_URI, produces = "application/json")
public class CategoryController {

    public static final String BASE_URI = "/api/v1/categories";

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public Flux<Category> index() {
        return categoryRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Category> show(@PathVariable String id) {
        return categoryRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Mono<Void> create(@RequestBody Publisher<Category> categoryStream) {
        return categoryRepository.saveAll(categoryStream).then();
    }

    @PutMapping("/{id}")
    public Mono<Category> update(@PathVariable String id, @RequestBody Category category) {
        return categoryRepository.findById(id).switchIfEmpty(Mono.just(category)).map(c -> {
            if (c.getId() != null && !c.getId().isEmpty()) {
                category.setId(id);
            }
            return category;
        }).flatMap(categoryRepository::save);
    }

    @PatchMapping("/{id}")
    public Mono<Category> patch(@PathVariable String id, @RequestBody Category category) {
        return categoryRepository.findById(id)
                                 .switchIfEmpty(
                                     Mono.error(
                                         new RuntimeException(
                                             "Category with ID " + id + " not found."
                                         )
                                     )
                                 )
                                 .map(c -> {
                                     category.setId(id);
                                     return category;
                                 })
                                 .flatMap(categoryRepository::save);
    }

}
