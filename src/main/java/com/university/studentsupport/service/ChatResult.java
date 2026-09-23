package com.university.studentsupport.service;

public record ChatResult(
        String answer,
        String source,
        boolean unresolved,
        boolean eligibleForAutoTicket,
        boolean systemError
) {
}