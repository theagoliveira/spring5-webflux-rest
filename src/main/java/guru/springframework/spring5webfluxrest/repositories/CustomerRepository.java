package guru.springframework.spring5webfluxrest.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import guru.springframework.spring5webfluxrest.domain.Customer;

public interface CustomerRepository extends ReactiveCrudRepository<Customer, String> {}
