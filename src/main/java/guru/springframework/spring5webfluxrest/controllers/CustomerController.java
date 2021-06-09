package guru.springframework.spring5webfluxrest.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.spring5webfluxrest.domain.Customer;
import guru.springframework.spring5webfluxrest.repositories.CustomerRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = CustomerController.BASE_URI, produces = "application/json")
public class CustomerController {

    public static final String BASE_URI = "/api/v1/customers";

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping
    public Flux<Customer> index() {
        return customerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Customer> show(@PathVariable String id) {
        return customerRepository.findById(id);
    }

}
