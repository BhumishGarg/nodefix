package com.university.studentsupport.model;

import jakarta.persistence.*;

@Entity
@Table(name = "faqs")
public class FAQ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;

    @Column(length = 500)
    private String question;

    @Column(length = 2000)
    private String answer;

    public FAQ() {
    }

    public FAQ(
            String category,
            String question,
            String answer) {

        this.category = category;
        this.question = question;
        this.answer = answer;
    }

    public Long getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}