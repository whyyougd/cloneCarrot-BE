package com.hanghea.clonecarrotbe.repository;

import com.hanghea.clonecarrotbe.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface StatusRepository extends JpaRepository<Status, Long> {
    Status findByStatus(String status);
}
