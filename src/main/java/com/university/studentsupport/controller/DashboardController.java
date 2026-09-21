package com.university.studentsupport.controller;

import com.university.studentsupport.repository.FAQRepository;
import com.university.studentsupport.repository.StudentRepository;
import com.university.studentsupport.repository.TicketRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final StudentRepository studentRepository;
    private final FAQRepository faqRepository;
    private final TicketRepository ticketRepository;

    public DashboardController(
            StudentRepository studentRepository,
            FAQRepository faqRepository,
            TicketRepository ticketRepository) {

        this.studentRepository = studentRepository;
        this.faqRepository = faqRepository;
        this.ticketRepository = ticketRepository;
    }

    @GetMapping
    public Map<String, Object> getDashboardData() {

        Map<String, Object> dashboard = new HashMap<>();

        dashboard.put("totalStudents", studentRepository.count());
        dashboard.put("totalFAQs", faqRepository.count());
        dashboard.put("totalTickets", ticketRepository.count());
        dashboard.put(
                "openTickets",
                ticketRepository.countByStatus("OPEN")
        );

        return dashboard;
    }

    @GetMapping("/health")
    public Map<String, String> healthCheck() {

        Map<String, String> response = new HashMap<>();

        response.put("status", "UP");
        response.put("service", "Student Support Agent");

        return response;
    }
}