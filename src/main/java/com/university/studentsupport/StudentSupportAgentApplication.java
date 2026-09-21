package com.university.studentsupport;

import com.university.studentsupport.model.FAQ;
import com.university.studentsupport.model.Student;
import com.university.studentsupport.repository.FAQRepository;
import com.university.studentsupport.repository.StudentRepository;
import com.university.studentsupport.service.UniversityFAQData;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class StudentSupportAgentApplication {

    public static void main(String[] args) {

        SpringApplication.run(
                StudentSupportAgentApplication.class,
                args
        );
    }

    @Bean
    CommandLineRunner loadSampleData(
            StudentRepository studentRepository,
            FAQRepository faqRepository,
            UniversityFAQData universityFAQData) {

        return args -> {

            // ==========================================
            // SAMPLE STUDENTS
            // ==========================================

            if (studentRepository.count() == 0) {

                studentRepository.save(
                        new Student(
                                "Aarav Sharma",
                                "aarav@university.edu",
                                "2024CSE001",
                                "CSE AI & ML",
                                "3"
                        )
                );

                studentRepository.save(
                        new Student(
                                "Ananya Verma",
                                "ananya@university.edu",
                                "2024CSE002",
                                "CSE AI & ML",
                                "3"
                        )
                );

                studentRepository.save(
                        new Student(
                                "Rohan Singh",
                                "rohan@university.edu",
                                "2024CSE003",
                                "CSE",
                                "3"
                        )
                );
            }

            // ==========================================
            // LOAD UNIVERSITY FAQs
            // ==========================================

            long faqCount = faqRepository.count();

            if (faqCount == 0) {

                faqRepository.saveAll(
                        universityFAQData.getFAQs()
                );

            } else if (faqCount < 100) {

                List<FAQ> allFAQs =
                        universityFAQData.getFAQs();

                for (FAQ newFAQ : allFAQs) {

                    boolean alreadyExists =
                            faqRepository
                                    .findByQuestionContainingIgnoreCase(
                                            newFAQ.getQuestion()
                                    )
                                    .stream()
                                    .anyMatch(
                                            existingFAQ ->
                                                    existingFAQ
                                                            .getQuestion()
                                                            .equalsIgnoreCase(
                                                                    newFAQ.getQuestion()
                                                            )
                                    );

                    if (!alreadyExists) {

                        faqRepository.save(newFAQ);
                    }

                    if (faqRepository.count() >= 100) {
                        break;
                    }
                }
            }

            // ==========================================
            // CONSOLE INFORMATION
            // ==========================================

            System.out.println(
                    "=============================================="
            );

            System.out.println(
                    "UniAssist sample data loaded successfully!"
            );

            System.out.println(
                    "Students: " +
                            studentRepository.count()
            );

            System.out.println(
                    "FAQs: " +
                            faqRepository.count()
            );

            System.out.println(
                    "=============================================="
            );
        };
    }
}