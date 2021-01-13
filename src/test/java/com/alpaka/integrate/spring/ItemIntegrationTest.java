package com.alpaka.integrate.spring;

import com.alpaka.model.customer.Customer;
import com.alpaka.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ItemIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void shouldNotAllowToPersistNullCustomer() {
        customerRepository.save(new Customer());
    }
}