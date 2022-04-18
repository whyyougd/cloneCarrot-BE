package com.hanghea.clonecarrotbe.repository;

import com.hanghea.clonecarrotbe.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
