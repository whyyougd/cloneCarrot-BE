package com.hanghea.clonecarrotbe.repository;

import com.hanghea.clonecarrotbe.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
