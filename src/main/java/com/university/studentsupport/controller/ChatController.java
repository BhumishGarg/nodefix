package com.university.studentsupport.controller;

import com.university.studentsupport.model.Ticket;
import com.university.studentsupport.repository.TicketRepository;
import com.university.studentsupport.service.ChatResult;
import com.university.studentsupport.service.ChatService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin
public class ChatController {

    private final ChatService chatService;
    private final TicketRepository ticketRepository;

    public ChatController(
            ChatService chatService,
            TicketRepository ticketRepository) {

        this.chatService = chatService;
        this.ticketRepository = ticketRepository;
    }


    @PostMapping
    public ResponseEntity<Map<String, Object>> chat(
            @RequestBody(required = false)
            Map<String, String> request) {

        Map<String, Object> response =
                new HashMap<>();


        // ==========================================
        // VALIDATE REQUEST
        // ==========================================

        if (request == null) {

            response.put(
                    "answer",
                    "Please enter a question."
            );

            response.put(
                    "source",
                    "NodeFix"
            );

            return ResponseEntity
                    .badRequest()
                    .body(response);
        }


        String message =
                request.get("message");

        if (message == null ||
            message.isBlank()) {

            response.put(
                    "answer",
                    "Please enter a question."
            );

            response.put(
                    "source",
                    "NodeFix"
            );

            return ResponseEntity
                    .badRequest()
                    .body(response);
        }


        String language =
                request.getOrDefault(
                        "language",
                        "English"
                );


        String studentName =
                cleanValue(
                        request.get("studentName")
                );

        String email =
                cleanValue(
                        request.get("email")
                );


        ChatResult result =
                chatService.processMessage(
                        message.trim(),
                        language
                );


        // ==========================================
        // SYSTEM ERROR
        // ==========================================

        if (result.systemError()) {

            response.put(
                    "answer",
                    result.answer()
            );

            response.put(
                    "source",
                    result.source()
            );

            response.put(
                    "agent",
                    "nodefix1"
            );

            response.put(
                    "ticketCreated",
                    false
            );

            return ResponseEntity
                    .status(
                            HttpStatus.BAD_GATEWAY
                    )
                    .body(response);
        }


        // ==========================================
        // AUTO TICKET DECISION
        // ==========================================

        boolean explicitTicketRequest =
                isExplicitTicketRequest(
                        message
                );

        boolean offTopic =
                isOffTopicResponse(
                        result.answer()
                );

        boolean shouldCreateTicket =
                (
                        result.unresolved()
                        ||
                        explicitTicketRequest
                )
                &&
                !offTopic;


        Ticket createdTicket = null;


        if (shouldCreateTicket) {

            String category =
                    detectCategory(
                            message
                    );


            String finalStudentName =
                    studentName.isBlank()
                            ? "NodeFix Student"
                            : studentName;


            String finalEmail =
                    email.isBlank()
                            ? "Not provided"
                            : email;


            String description =
                    buildTicketDescription(
                            message,
                            result.answer()
                    );


            createdTicket =
                    new Ticket(
                            finalStudentName,
                            finalEmail,
                            category,
                            description
                    );


            createdTicket =
                    ticketRepository.save(
                            createdTicket
                    );
        }


        // ==========================================
        // RESPONSE
        // ==========================================

        response.put(
                "answer",
                result.answer()
        );

        response.put(
                "source",
                result.source()
        );

        response.put(
                "agent",
                "nodefix1"
        );

        response.put(
                "ticketCreated",
                createdTicket != null
        );


        if (createdTicket != null) {

            response.put(
                    "ticketId",
                    createdTicket.getId()
            );

            response.put(
                    "ticketCategory",
                    createdTicket.getCategory()
            );

            response.put(
                    "ticketStatus",
                    createdTicket.getStatus()
            );
        }


        return ResponseEntity.ok(
                response
        );
    }


    // ==========================================
    // EXPLICIT TICKET REQUEST
    // ==========================================

    private boolean isExplicitTicketRequest(
            String message) {

        if (message == null) {
            return false;
        }

        String text =
                message
                        .toLowerCase()
                        .trim();

        String[] phrases = {

                "raise a ticket",
                "create a ticket",
                "open a ticket",
                "make a ticket",
                "submit a ticket",

                "raise ticket",
                "create ticket",
                "open ticket",

                "human support",
                "human help",
                "talk to support",
                "contact support",
                "need support",

                "i still need help",
                "i need help from support",
                "connect me to support"

        };


        for (String phrase : phrases) {

            if (text.contains(phrase)) {
                return true;
            }
        }

        return false;
    }


    // ==========================================
    // OFF-TOPIC PROTECTION
    // ==========================================

    private boolean isOffTopicResponse(
            String answer) {

        if (answer == null) {
            return false;
        }

        String normalized =
                answer
                        .trim()
                        .toLowerCase();


        return normalized.contains(
                "i can only help with chitkara university"
        )
        ||
        normalized.contains(
                "i can only help with chitkara"
        )
        ||
        normalized.contains(
                "only help with chitkara university"
        )
        ||
        normalized.contains(
                "only help with university-related"
        );
    }


    // ==========================================
    // CATEGORY DETECTION
    // ==========================================

    private String detectCategory(
            String message) {

        String text =
                message
                        .toLowerCase();


        if (containsAny(
                text,
                "fee",
                "fees",
                "payment",
                "pay",
                "refund",
                "fine",
                "tuition"
        )) {
            return "Fees";
        }


        if (containsAny(
                text,
                "exam",
                "examination",
                "test",
                "marks",
                "result",
                "reappear",
                "sessional",
                "st1",
                "st2"
        )) {
            return "Examination";
        }


        if (containsAny(
                text,
                "attendance",
                "attendence"
        )) {
            return "Attendance";
        }


        if (containsAny(
                text,
                "hostel",
                "room",
                "warden",
                "mess"
        )) {
            return "Hostel";
        }


        if (containsAny(
                text,
                "library",
                "book",
                "books",
                "librarian"
        )) {
            return "Library";
        }


        if (containsAny(
                text,
                "wifi",
                "wi-fi",
                "password",
                "login",
                "portal",
                "chalkpad",
                "email",
                "internet",
                "technical",
                "computer"
        )) {
            return "Technical";
        }


        if (containsAny(
                text,
                "course",
                "subject",
                "semester",
                "academic",
                "registration",
                "class",
                "faculty"
        )) {
            return "Academic";
        }


        if (containsAny(
                text,
                "admission",
                "admissions",
                "apply",
                "application",
                "enrol"
        )) {
            return "Admissions";
        }


        if (containsAny(
                text,
                "placement",
                "placements",
                "internship",
                "job",
                "career"
        )) {
            return "Placement";
        }


        return "Other";
    }


    private boolean containsAny(
            String text,
            String... values) {

        for (String value : values) {

            if (text.contains(value)) {
                return true;
            }
        }

        return false;
    }


    // ==========================================
    // TICKET DESCRIPTION
    // ==========================================

    private String buildTicketDescription(
            String question,
            String aiResponse) {

        StringBuilder description =
                new StringBuilder();

        description.append(
                "Automatically created from NodeFix AI chat.\n\n"
        );

        description.append(
                "Student question:\n"
        );

        description.append(
                question.trim()
        );

        description.append(
                "\n\n"
        );

        description.append(
                "NodeFix response:\n"
        );

        description.append(
                aiResponse == null
                        ? ""
                        : aiResponse.trim()
        );

        return truncate(
                description.toString(),
                1900
        );
    }


    // ==========================================
    // SAFE STRING
    // ==========================================

    private String cleanValue(
            String value) {

        if (value == null) {
            return "";
        }

        return value.trim();
    }


    private String truncate(
            String value,
            int maxLength) {

        if (value == null) {
            return "";
        }

        if (value.length() <= maxLength) {
            return value;
        }

        return value.substring(
                0,
                maxLength
        );
    }
}