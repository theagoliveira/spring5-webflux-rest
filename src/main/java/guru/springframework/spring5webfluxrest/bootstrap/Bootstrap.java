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
        var fruits = new Category();
        fruits.setName("Fruits");
        categoryRepository.save(fruits).block();

        var dried = new Category();
        dried.setName("Dried");
        categoryRepository.save(dried).block();

        var fresh = new Category();
        fresh.setName("Fresh");
        categoryRepository.save(fresh).block();

        var exotic = new Category();
        exotic.setName("Exotic");
        categoryRepository.save(exotic).block();

        var nuts = new Category();
        nuts.setName("Nuts");
        categoryRepository.save(nuts).block();

        log.info(categoryRepository.count().block() + " categories loaded.");
    }

    private void loadCustomers() {
        var customer1 = new Customer();
        customer1.setFirstName("Susan");
        customer1.setLastName("Tanner");
        customerRepository.save(customer1).block();

        var customer2 = new Customer();
        customer2.setFirstName("Freddy");
        customer2.setLastName("Meyers");
        customerRepository.save(customer2).block();

        var customer3 = new Customer();
        customer3.setFirstName("Joe");
        customer3.setLastName("Buck");
        customerRepository.save(customer3).block();

        var customer4 = new Customer();
        customer4.setFirstName("Michael");
        customer4.setLastName("Weston");
        customerRepository.save(customer4).block();

        log.info(customerRepository.count().block() + " customers loaded.");
    }

}
