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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Mono<Void> create(@RequestBody Publisher<Customer> customerStream) {
        return customerRepository.saveAll(customerStream).then();
    }

    @PutMapping("/{id}")
    public Mono<Customer> update(@PathVariable String id, @RequestBody Customer customer) {
        customer.setId(id);
        return customerRepository.save(customer);
    }

    @PatchMapping("/{id}")
    public Mono<Customer> patch(@PathVariable String id, @RequestBody Customer customer) {
        return customerRepository.findById(id)
                                 .switchIfEmpty(
                                     Mono.error(
                                         new RuntimeException(
                                             "Customer with ID " + id + " not found."
                                         )
                                     )
                                 )
                                 .map(c -> {
                                     customer.setId(id);
                                     return customer;
                                 })
                                 .flatMap(customerRepository::save);
    }

}
