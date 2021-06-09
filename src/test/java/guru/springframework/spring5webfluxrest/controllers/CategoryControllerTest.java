package guru.springframework.spring5webfluxrest.controllers;

import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;

import guru.springframework.spring5webfluxrest.domain.Category;
import guru.springframework.spring5webfluxrest.repositories.CategoryRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class CategoryControllerTest {

    private static final String NAME1 = "name1";
    private static final String NAME2 = "name2";

    WebTestClient webTestClient;
    CategoryRepository categoryRepository;
    CategoryController categoryController;

    @BeforeEach
    void setUp() {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryController = new CategoryController(categoryRepository);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    void testIndex() {
        BDDMockito.given(categoryRepository.findAll())
                  .willReturn(
                      Flux.just(
                          Category.builder().name(NAME1).build(),
                          Category.builder().name(NAME2).build()
                      )
                  );

        webTestClient.get()
                     .uri(CategoryController.BASE_URI)
                     .exchange()
                     .expectBodyList(Category.class)
                     .hasSize(2);
    }

    @Test
    void testShow() {
        BDDMockito.given(categoryRepository.findById(anyString()))
                  .willReturn(Mono.just(Category.builder().name(NAME1).build()));

        webTestClient.get()
                     .uri(CategoryController.BASE_URI + "/test")
                     .exchange()
                     .expectBody(Category.class);
    }

}
