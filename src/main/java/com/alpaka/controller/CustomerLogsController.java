package com.alpaka.controller;

import com.alpaka.model.customerLogs.CustomerLogs;
import com.alpaka.service.CustomerLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerLogsController {

    @Autowired
    private CustomerLogsService customerLogsService;

    @GetMapping("/logs/customer/{customerId}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<List<CustomerLogs>> getCustomerLogs(@PathVariable("customerId") Long customerId, @RequestParam int page, @RequestParam int size) {
        return customerLogsService.getActivityData(customerId, page, size);
    }
}
