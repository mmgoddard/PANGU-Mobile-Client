package com.service;

import com.domain.PanguModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Created by Mark on 23/01/15.
 */
public interface APIRepository extends JpaRepository<PanguModel, Long> {
    public List<PanguModel> findAll();
    public PanguModel findById(Long id);
}
