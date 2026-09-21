package com.university.studentsupport.controller;

import com.university.studentsupport.model.FAQ;
import com.university.studentsupport.repository.FAQRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faqs")
@CrossOrigin
public class FAQController {

    private final FAQRepository faqRepository;

    public FAQController(FAQRepository faqRepository) {
        this.faqRepository = faqRepository;
    }

    @GetMapping
    public List<FAQ> getAllFAQs() {
        return faqRepository.findAll();
    }

    @GetMapping("/search")
    public List<FAQ> searchFAQs(
            @RequestParam String keyword) {

        return faqRepository
                .findByQuestionContainingIgnoreCase(keyword);
    }
}