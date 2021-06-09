package guru.springframework.spring5webfluxrest.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;

import guru.springframework.spring5webfluxrest.domain.Customer;
import guru.springframework.spring5webfluxrest.repositories.CustomerRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class CustomerControllerTest {

    private static final String FIRST_NAME1 = "firstName1";
    private static final String LAST_NAME1 = "lastName1";
    private static final String FIRST_NAME2 = "firstName2";
    private static final String LAST_NAME2 = "lastName2";

    WebTestClient webTestClient;
    CustomerRepository customerRepository;
    CustomerController customerController;

    @BeforeEach
    void setUp() {
        customerRepository = Mockito.mock(CustomerRepository.class);
        customerController = new CustomerController(customerRepository);
        webTestClient = WebTestClient.bindToController(customerController).build();
    }

    @Test
    void testIndex() {
        BDDMockito.given(customerRepository.findAll())
                  .willReturn(
                      Flux.just(
                          Customer.builder().firstName(FIRST_NAME1).lastName(LAST_NAME1).build(),
                          Customer.builder().firstName(FIRST_NAME2).lastName(LAST_NAME2).build()
                      )
                  );

        webTestClient.get()
                     .uri(CustomerController.BASE_URI)
                     .exchange()
                     .expectBodyList(Customer.class)
                     .hasSize(2);
    }

    @Test
    void testShow() {
        BDDMockito.given(customerRepository.findById(anyString()))
                  .willReturn(
                      Mono.just(
                          Customer.builder().firstName(FIRST_NAME1).lastName(LAST_NAME1).build()
                      )
                  );

        webTestClient.get()
                     .uri(CustomerController.BASE_URI + "/test")
                     .exchange()
                     .expectBody(Customer.class);
    }

    @Test
    void testCreate() {
        BDDMockito.given(customerRepository.saveAll(any(Publisher.class)))
                  .willReturn(
                      Flux.just(
                          Customer.builder().firstName(FIRST_NAME1).lastName(LAST_NAME1).build()
                      )
                  );

        Mono<Customer> monoCustomer = Mono.just(
            Customer.builder().firstName(FIRST_NAME1).lastName(LAST_NAME1).build()
        );

        webTestClient.post()
                     .uri(CustomerController.BASE_URI)
                     .body(monoCustomer, Customer.class)
                     .exchange()
                     .expectStatus()
                     .isCreated();
    }

    @Test
    void testUpdate() {
        BDDMockito.given(customerRepository.save(any(Customer.class)))
                  .willReturn(
                      Mono.just(
                          Customer.builder().firstName(FIRST_NAME1).lastName(LAST_NAME1).build()
                      )
                  );

        Mono<Customer> monoCustomer = Mono.just(
            Customer.builder().firstName(FIRST_NAME1).lastName(LAST_NAME1).build()
        );

        webTestClient.put()
                     .uri(CustomerController.BASE_URI + "/test")
                     .body(monoCustomer, Customer.class)
                     .exchange()
                     .expectStatus()
                     .isOk();
    }

    @Test
    void testPatch() {
        BDDMockito.given(customerRepository.findById(anyString()))
                  .willReturn(
                      Mono.just(
                          Customer.builder().firstName(FIRST_NAME1).lastName(LAST_NAME1).build()
                      )
                  );
        BDDMockito.given(customerRepository.save(any(Customer.class)))
                  .willReturn(
                      Mono.just(
                          Customer.builder().firstName(FIRST_NAME1).lastName(LAST_NAME1).build()
                      )
                  );

        Mono<Customer> monoCustomer = Mono.just(
            Customer.builder().firstName(FIRST_NAME1).lastName(LAST_NAME1).build()
        );

        webTestClient.patch()
                     .uri(CustomerController.BASE_URI + "/test")
                     .body(monoCustomer, Customer.class)
                     .exchange()
                     .expectStatus()
                     .isOk();
    }

    @Test
    void testPatchNotFound() {
        BDDMockito.given(customerRepository.findById(anyString())).willReturn(Mono.empty());

        Mono<Customer> monoCustomer = Mono.just(
            Customer.builder().firstName(FIRST_NAME1).lastName(LAST_NAME1).build()
        );

        webTestClient.patch()
                     .uri(CustomerController.BASE_URI + "/unknown")
                     .body(monoCustomer, Customer.class)
                     .exchange()
                     .expectStatus()
                     .is5xxServerError();
    }

}
