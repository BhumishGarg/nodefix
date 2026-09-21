package com.university.studentsupport.service;

import com.university.studentsupport.model.FAQ;
import com.university.studentsupport.repository.FAQRepository;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChatService {

    private final FAQRepository faqRepository;

    public ChatService(FAQRepository faqRepository) {
        this.faqRepository = faqRepository;
    }

    public Map<String, Object> processMessage(String message) {

        Map<String, Object> response =
                new LinkedHashMap<>();

        // ==========================================
        // EMPTY MESSAGE
        // ==========================================

        if (message == null || message.trim().isEmpty()) {

            response.put(
                    "answer",
                    "Please enter a question."
            );

            response.put(
                    "source",
                    "system"
            );

            return response;
        }

        String query =
                message.toLowerCase().trim();


        // ==========================================
        // GREETING
        // ==========================================

        if (query.matches(
                ".*\\b(hello|hi|hey|namaste)\\b.*"
        )) {

            response.put(
                    "answer",
                    "Hello! 👋 I am UniAssist, your University Student Support Agent. How can I help you?"
            );

            response.put(
                    "source",
                    "UniAssist"
            );

            return response;
        }


        // ==========================================
        // GET ALL FAQs
        // ==========================================

        List<FAQ> allFaqs =
                faqRepository.findAll();


        // ==========================================
        // COMMON WORDS TO IGNORE
        // ==========================================

        String[] stopWords = {

                "what",
                "when",
                "where",
                "why",
                "how",
                "can",
                "could",
                "would",
                "should",
                "is",
                "are",
                "the",
                "a",
                "an",
                "to",
                "for",
                "of",
                "my",
                "i",
                "me",
                "do",
                "does",
                "will",
                "and",
                "or",
                "in",
                "on",
                "please",
                "tell",
                "about",
                "get",
                "find"
        };


        Set<String> stopWordsSet =
                new HashSet<>(
                        Arrays.asList(stopWords)
                );


        // ==========================================
        // SPLIT USER QUESTION
        // ==========================================

        Set<String> queryWords =
                Arrays.stream(
                                query
                                        .replaceAll(
                                                "[^a-z0-9 ]",
                                                " "
                                        )
                                        .split("\\s+")
                        )

                        .filter(
                                word ->
                                        word.length() > 2
                                                &&
                                        !stopWordsSet.contains(word)
                        )

                        .collect(
                                Collectors.toSet()
                        );


        // ==========================================
        // FIND BEST FAQ
        // ==========================================

        FAQ bestFAQ = null;

        int bestScore = 0;


        for (FAQ faq : allFaqs) {

            String faqText =
                    (
                            faq.getQuestion()
                                    + " "
                                    + faq.getCategory()
                    )
                            .toLowerCase()
                            .replaceAll(
                                    "[^a-z0-9 ]",
                                    " "
                            );


            Set<String> faqWords =
                    new HashSet<>(
                            Arrays.asList(
                                    faqText.split("\\s+")
                            )
                    );


            int score = 0;


            // Compare words
            for (String word : queryWords) {

                if (faqWords.contains(word)) {

                    score++;
                }
            }


            // Category bonus
            if (
                    query.contains(
                            faq.getCategory()
                                    .toLowerCase()
                    )
            ) {

                score += 2;
            }


            // Exact question bonus
            if (
                    query.equals(
                            faq.getQuestion()
                                    .toLowerCase()
                    )
            ) {

                score += 10;
            }


            // Best match
            if (score > bestScore) {

                bestScore = score;

                bestFAQ = faq;
            }
        }


        // ==========================================
        // RETURN MATCHED ANSWER
        // ==========================================

        if (
                bestFAQ != null
                        &&
                bestScore >= 1
        ) {

            response.put(
                    "answer",
                    bestFAQ.getAnswer()
            );

            response.put(
                    "source",
                    "University FAQ"
            );

            response.put(
                    "category",
                    bestFAQ.getCategory()
            );

            response.put(
                    "matchedQuestion",
                    bestFAQ.getQuestion()
            );

            return response;
        }


        // ==========================================
        // FALLBACK
        // ==========================================

        response.put(
                "answer",
                "I couldn't find a specific answer in the university knowledge base. Try asking about admissions, courses, examinations, attendance, fees, library, student portal, hostel, support tickets, internships or placements."
        );

        response.put(
                "source",
                "UniAssist"
        );

        return response;
    }
}