package com.digdes.simple.dao;

import com.digdes.simple.model.SimpleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SimpleRepository extends JpaRepository<SimpleModel, Long> {
    Optional<SimpleModel> findByName(String name);
}
