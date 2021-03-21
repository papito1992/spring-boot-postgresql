package com.alpaka.controller;

import com.alpaka.dtos.response.MessageResponse;
import com.alpaka.model.customer.Customer;
import com.alpaka.service.CustomerService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> getCustomerList() {
        return customerService.getCustomerList();
    }

    @GetMapping("/customers/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") String customerId) {
        return customerService.getCustomer(customerId);
    }

    @SneakyThrows
    @PostMapping("/customer")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @DeleteMapping("/customers/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") Long id) {
      return customerService.deleteCustomer(id);
    }

    @PutMapping("/customers/{id}/delete-representative")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> deleteCustomerRepresentative(@PathVariable("id") Long id) {
        return customerService.deleteCustomerRepresentative(id);
    }

    @PutMapping("/customers/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> updateCustomer(@PathVariable("id") Long id, @RequestBody Customer customer) {
        System.out.println(id);
        return customerService.updateCustomer(id, customer);
    }

    @PostMapping("/customers/{customerId}/add-representative/{repIsbn}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> addRepresentativeToCustomer(@PathVariable("customerId") Long customerId, @PathVariable("repIsbn") String repIsbn) {
        return customerService.addOrUpdateCustomerRepresentative(customerId, repIsbn);
    }
}
