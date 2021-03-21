package com.alpaka.service;

import com.alpaka.model.customer.Customer;
import com.alpaka.model.customerLogs.CustomerActivity;
import com.alpaka.model.customerLogs.CustomerLogs;
import com.alpaka.model.user.User;
import com.alpaka.repository.CustomerLogsRepository;
import com.alpaka.repository.CustomerRepository;
import com.alpaka.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerLogsService {

    @Autowired
    private CustomerLogsRepository customerLogsRepository;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRepository userRepository;


    public void storeActivityData(String username, CustomerActivity customerActivity) {
        CustomerLogs customerLogs = new CustomerLogs();
        customerLogs.setUsername(username);
        customerLogs.setActionName(customerActivity.getActionName());
        customerLogs.setIpAddress(customerActivity.getIpAddress());
        customerLogs.setResponseStatus(customerActivity.getResponseStatus());
        customerLogs.setVisitedEndpoint(customerActivity.getVisitedEndpoint());
        customerLogs.setCustomerActivity("customerActivity");
        customerLogsRepository.save(customerLogs);

    }

    public ResponseEntity<List<CustomerLogs>> getActivityData(Long customerId, int page, int size) {
//        Optional<Customer> customer = customerRepository.findById(customerId);
        Optional<User> user = userRepository.findById(customerId);
        Pageable firstPageWithTwoElements = PageRequest.of(page, size);
        Long numberOfActivities = 0L;
        List<CustomerLogs> customerLogsList = new ArrayList<>();
        if (user.isPresent()){
        numberOfActivities = customerLogsRepository.countAllByUsername(user.get().getUsername());
        customerLogsList= customerLogsRepository.findByUsername(user.get().getUsername(),firstPageWithTwoElements);
        }
//        return customerLogsRepository.findByUsername(user.get().getUsername(),firstPageWithTwoElements);
        System.out.println(numberOfActivities);
        return ResponseEntity.ok().header("customerLogsTotalActivityCount", numberOfActivities.toString())
                .body(customerLogsList);
    }

}
