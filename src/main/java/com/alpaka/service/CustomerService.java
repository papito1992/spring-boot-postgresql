package com.alpaka.service;

import com.alpaka.dtos.response.MessageResponse;
import com.alpaka.exceptions.EntityNotFoundException;
import com.alpaka.exceptions.Error;
import com.alpaka.model.customer.Customer;
import com.alpaka.model.representative.Representative;
import com.alpaka.repository.CustomerRepository;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.hibernate.JDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class CustomerService extends Throwable {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RepresentativeService representativeService;

    public ResponseEntity<?> getCustomerList() {
        List<Customer> customerList;
        try {
            customerList = customerRepository.findAll();

            int customerListSize = customerList.size();
            String customerListSizeToString;
            if (customerList.isEmpty()) {
                customerListSizeToString = String.valueOf(customerListSize);
                return ResponseEntity.ok().header("total-count", customerListSizeToString).body(customerList);
            }
            return ResponseEntity.ok().header("total-count", "0").body(customerList);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Customer> getCustomer(String customerId) {
        try {
            Optional<Customer> customer = customerRepository.findById(Long.parseLong(customerId));
            return customer.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @SneakyThrows
//    public Customer getCustomerByUsername(String username) {
//        try {
//            Customer customer = customerRepository.findByUsername(username);
//            return customer;
//        } catch (Exception e) {
//            log.error(e.getLocalizedMessage());
//            throw new Exception(e.getLocalizedMessage());
//        }
//    }

    public ResponseEntity<?> createCustomer(Customer customer) throws SQLException {
        Representative representative = new Representative();
        Customer _customer = new Customer();
        try {
            representative = representativeService.getRepresentative(customer.getRepresentativeIsbn());
            //Setting false for creation to indicate that current rep is only a temporary one
            boolean hasRepresentation = false;

            _customer = customerRepository
                    .save(new Customer(customer.getUsername(), customer.getEmail(), representative,
                            hasRepresentation));

            return new ResponseEntity<>(_customer, HttpStatus.CREATED);
        } catch (JDBCException e) {
            throw e.getSQLException();
        } catch (DataIntegrityViolationException ex) {
            Error error = new Error();
            error.setMessage(ex.getCause().toString());
            log.error("getMostSpecificCause" + ex.getMostSpecificCause());
            return new ResponseEntity<>(error, HttpStatus.EXPECTATION_FAILED);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            Error error = new Error();
            error.setMessage(ex.getLocalizedMessage());
            return new ResponseEntity<>(error, HttpStatus.EXPECTATION_FAILED);
        }
    }

    public ResponseEntity<?> deleteCustomer(Long id) {
        try {
            if (customerRepository.existsById(id)) {
                customerRepository.deleteById(id);
            } else {
                MessageResponse messageResponse = new MessageResponse("deleted NOK");
                return ResponseEntity.badRequest().body(messageResponse);
            }
        } catch (Exception e) {
            log.warn("Failed to delete customer with id: {}", id);
            log.warn(e.getMessage());
            throw new EntityNotFoundException(Customer.class, "Could not delete this customer!");
        }
        MessageResponse messageResponse = new MessageResponse("deleted OK");
        return ResponseEntity.ok().body(messageResponse);
    }

    public ResponseEntity<?> deleteCustomerRepresentative(Long id) {
        try {
            Optional<Object> customer = customerRepository.findById(id)
                    .map(existingCustomer -> {
                        existingCustomer.setHasRepresentation(false);
                        existingCustomer.setRepresentativeIsbn(null);
                        customerRepository.save(existingCustomer);
                        return existingCustomer;
                    });
            return new ResponseEntity<>(customer, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.warn("Failed to delete customer with id: {}", id);
            log.warn(e.getMessage());
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    public ResponseEntity<?> addOrUpdateCustomerRepresentative(Long customerId, String repIsbn) {
        try {
            Representative representative = representativeService.getRepresentative(repIsbn);
            Customer customer = customerRepository.findById(customerId).
                    orElseThrow(() -> new EntityNotFoundException(Customer.class, "Customer not found"));
            customer.setRepresentativeIsbn(representative.getIsbn());
            customer.setHasRepresentation(true);
            customerRepository.save(customer);
            return new ResponseEntity<>(customer, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);

        }
    }

    public ResponseEntity<?> updateCustomer(Long customerId, Customer updatedCustomerData) {
        log.info(updatedCustomerData.getCustomerId());
        try {
            Customer existingCustomer = customerRepository.findById(customerId).
                    orElseThrow(() -> new EntityNotFoundException(Customer.class, "Customer not found"));
            // checks if representative exists
            representativeService.getRepresentative(updatedCustomerData.getRepresentativeIsbn());

            existingCustomer.setUsername(updatedCustomerData.getUsername());
            existingCustomer.setEmail(updatedCustomerData.getEmail());
            existingCustomer.setRepresentativeIsbn(updatedCustomerData.getRepresentativeIsbn());
            existingCustomer.setHasRepresentation(updatedCustomerData.getHasRepresentation());
            customerRepository.save(existingCustomer);
            return new ResponseEntity<>(existingCustomer, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);

        }
    }

}
