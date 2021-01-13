package com.alpaka.repository;

import com.alpaka.model.representative.Representative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepresentativeRepository extends JpaRepository<Representative, Long> {

    Representative findByIsbn(String isbn);
}
