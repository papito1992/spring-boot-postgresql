package com.alpaka.service;

import com.alpaka.model.representative.Representative;
import com.alpaka.repository.RepresentativeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class RepresentativeService {

    @Autowired
    private RepresentativeRepository representativeRepository;

    public ResponseEntity<List<Representative>> getRepresentativeList() {
        List<Representative> representativeList;
        try {
            representativeList = representativeRepository.findAll();

            if (representativeList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(representativeList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Representative getRepresentative(String isbn) {
        try {
            Representative representative = representativeRepository.findByIsbn(isbn);
            return representative;
        } catch (Exception e) {
            log.warn("REPRESENTATIVE NOT FOUNDWITH UNIQUE NAME: {}", isbn);
            log.error(e.getLocalizedMessage());
            return null;
        }
    }

    public ResponseEntity<Representative> createRepresentative(Representative representative) {
        try {
            Representative _representative = representativeRepository
                    .save(new Representative(representative.getUsername(), representative.getEmail(), representative.getIsbn()));
            return new ResponseEntity<>(_representative, HttpStatus.CREATED);
        } catch (Exception e) {
            log.warn(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    public ResponseEntity<HttpStatus> deleteRepresentative(Long id) {
        try {
            representativeRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.warn("Failed to delete representative with id: {}", id);
            log.warn(e.getMessage());
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
