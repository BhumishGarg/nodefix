package com.university.studentsupport.repository;

import com.university.studentsupport.model.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FAQRepository extends JpaRepository<FAQ, Long> {

    List<FAQ> findByQuestionContainingIgnoreCase(String keyword);

    List<FAQ> findByCategoryIgnoreCase(String category);
}