package com.service;

import com.domain.TestModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Created by Mark on 23/01/15.
 */
public interface APIRepository extends JpaRepository<TestModel, Long> {
    List<TestModel> findAll();
}
