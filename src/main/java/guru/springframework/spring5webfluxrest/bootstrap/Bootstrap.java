package guru.springframework.spring5webfluxrest.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springframework.spring5webfluxrest.domain.Category;
import guru.springframework.spring5webfluxrest.domain.Customer;
import guru.springframework.spring5webfluxrest.repositories.CategoryRepository;
import guru.springframework.spring5webfluxrest.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count().block() == 0) {
            log.info("Loading categories...");
            loadCategories();
        } else {
            log.info("Categories already loaded.");
        }

        if (customerRepository.count().block() == 0) {
            log.info("Loading customers...");
            loadCustomers();
        } else {
            log.info("Customers already loaded.");
        }
    }

    private void loadCategories() {
        categoryRepository.save(Category.builder().name("Fruits").build()).block();
        categoryRepository.save(Category.builder().name("Dried").build()).block();
        categoryRepository.save(Category.builder().name("Fresh").build()).block();
        categoryRepository.save(Category.builder().name("Exotic").build()).block();
        categoryRepository.save(Category.builder().name("Nuts").build()).block();

        log.info(categoryRepository.count().block() + " categories loaded.");
    }

    private void loadCustomers() {
        customerRepository.save(Customer.builder().firstName("Susan").lastName("Tanner").build())
                          .block();
        customerRepository.save(Customer.builder().firstName("Freddy").lastName("Meyers").build())
                          .block();
        customerRepository.save(Customer.builder().firstName("Joe").lastName("Buck").build())
                          .block();
        customerRepository.save(Customer.builder().firstName("Michael").lastName("Weston").build())
                          .block();

        log.info(customerRepository.count().block() + " customers loaded.");
    }

}
