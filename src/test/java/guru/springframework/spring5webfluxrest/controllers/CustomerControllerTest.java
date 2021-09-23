package guru.springframework.spring5webfluxrest.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        given(customerRepository.findAll()).willReturn(
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
        given(customerRepository.findById(anyString())).willReturn(
            Mono.just(Customer.builder().firstName(FIRST_NAME1).lastName(LAST_NAME1).build())
        );

        webTestClient.get()
                     .uri(CustomerController.BASE_URI + "/test")
                     .exchange()
                     .expectBody(Customer.class);
    }

    @Test
    void testCreate() {
        given(customerRepository.saveAll(any(Publisher.class)))
                                                               .willReturn(
                                                                   Flux.just(
                                                                       Customer.builder()
                                                                               .firstName(
                                                                                   FIRST_NAME1
                                                                               )
                                                                               .lastName(LAST_NAME1)
                                                                               .build()
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
        given(customerRepository.save(any(Customer.class))).willReturn(
            Mono.just(Customer.builder().firstName(FIRST_NAME1).lastName(LAST_NAME1).build())
        );

        given(customerRepository.findById(anyString())).willReturn(
            Mono.just(Customer.builder().firstName(FIRST_NAME1).lastName(LAST_NAME1).build())
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
    void testCreateWithPut() {
        given(customerRepository.save(any(Customer.class))).willReturn(
            Mono.just(Customer.builder().firstName(FIRST_NAME1).lastName(LAST_NAME1).build())
        );

        given(customerRepository.findById(anyString())).willReturn(Mono.empty());

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
        given(customerRepository.findById(anyString())).willReturn(
            Mono.just(Customer.builder().firstName(FIRST_NAME1).lastName(LAST_NAME1).build())
        );
        given(customerRepository.save(any(Customer.class))).willReturn(
            Mono.just(Customer.builder().firstName(FIRST_NAME1).lastName(LAST_NAME1).build())
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

        verify(customerRepository).findById(anyString());
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void testPatchNotFound() {
        given(customerRepository.findById(anyString())).willReturn(Mono.empty());

        Mono<Customer> monoCustomer = Mono.just(
            Customer.builder().firstName(FIRST_NAME1).lastName(LAST_NAME1).build()
        );

        webTestClient.patch()
                     .uri(CustomerController.BASE_URI + "/unknown")
                     .body(monoCustomer, Customer.class)
                     .exchange()
                     .expectStatus()
                     .is5xxServerError();

        verify(customerRepository).findById(anyString());
        verify(customerRepository, never()).save(any(Customer.class));
    }

}
