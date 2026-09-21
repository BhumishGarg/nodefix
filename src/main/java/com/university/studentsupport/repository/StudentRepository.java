package com.university.studentsupport.repository;

import com.university.studentsupport.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

}