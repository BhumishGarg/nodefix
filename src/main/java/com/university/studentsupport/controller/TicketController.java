package com.university.studentsupport.controller;

import com.university.studentsupport.model.Ticket;
import com.university.studentsupport.repository.TicketRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin
public class TicketController {

    private final TicketRepository ticketRepository;

    public TicketController(
            TicketRepository ticketRepository) {

        this.ticketRepository =
                ticketRepository;
    }

    @GetMapping
    public List<Ticket> getAllTickets() {

        return ticketRepository
                .findAllByOrderByCreatedAtDesc();
    }

    @PostMapping
    public Ticket createTicket(
            @RequestBody Ticket ticket) {

        if (ticket.getStatus() == null ||
            ticket.getStatus().isBlank()) {

            ticket.setStatus("OPEN");
        }

        return ticketRepository.save(
                ticket
        );
    }
}