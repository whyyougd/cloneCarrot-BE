package com.hanghea.clonecarrotbe.repository;

import com.hanghea.clonecarrotbe.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
//    <T> Optional<T> findByUsername(String username);

}
