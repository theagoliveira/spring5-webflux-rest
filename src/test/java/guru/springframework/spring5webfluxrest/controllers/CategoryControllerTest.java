package guru.springframework.spring5webfluxrest.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
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

    @Test
    void testCreate() {
        BDDMockito.given(categoryRepository.saveAll(any(Publisher.class)))
                  .willReturn(Flux.just(Category.builder().name(NAME1).build()));

        Mono<Category> monoCategory = Mono.just(Category.builder().name(NAME1).build());

        webTestClient.post()
                     .uri(CategoryController.BASE_URI)
                     .body(monoCategory, Category.class)
                     .exchange()
                     .expectStatus()
                     .isCreated();
    }

    @Test
    void testUpdate() {
        BDDMockito.given(categoryRepository.save(any(Category.class)))
                  .willReturn(Mono.just(Category.builder().name(NAME1).build()));

        Mono<Category> monoCategory = Mono.just(Category.builder().name(NAME1).build());

        webTestClient.put()
                     .uri(CategoryController.BASE_URI + "/test")
                     .body(monoCategory, Category.class)
                     .exchange()
                     .expectStatus()
                     .isOk();
    }

    @Test
    void testPatch() {
        BDDMockito.given(categoryRepository.findById(anyString()))
                  .willReturn(Mono.just(Category.builder().name(NAME1).build()));
        BDDMockito.given(categoryRepository.save(any(Category.class)))
                  .willReturn(Mono.just(Category.builder().name(NAME1).build()));

        Mono<Category> monoCategory = Mono.just(Category.builder().name(NAME1).build());

        webTestClient.patch()
                     .uri(CategoryController.BASE_URI + "/test")
                     .body(monoCategory, Category.class)
                     .exchange()
                     .expectStatus()
                     .isOk();
    }

    @Test
    void testPatchNotFound() {
        BDDMockito.given(categoryRepository.findById(anyString())).willReturn(Mono.empty());

        Mono<Category> monoCategory = Mono.just(Category.builder().name(NAME1).build());

        webTestClient.patch()
                     .uri(CategoryController.BASE_URI + "/unknown")
                     .body(monoCategory, Category.class)
                     .exchange()
                     .expectStatus()
                     .is5xxServerError();
    }

}
