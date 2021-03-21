package com.alpaka.repository;

import com.alpaka.model.customerLogs.CustomerLogs;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerLogsRepository extends PagingAndSortingRepository<CustomerLogs, Long> {

    @Override
    boolean existsById(Long userId);

    List<CustomerLogs> findByUsername(String username, Pageable pageable);

    Long countAllByUsername(String username);
}
