package com.vogoun.it_num_assessment.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vogoun.it_num_assessment.models.Ticket;
import com.vogoun.it_num_assessment.repositories.TicketRepository;

@RestController
public class TicketController {
    private final TicketRepository repository;

    TicketController(TicketRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/tickets")
    List<Ticket> all() {
        return repository.findAll();
    }

    @PostMapping("/tickets")
    Ticket newTicket(@RequestBody Ticket newTicket) {
        return repository.save(newTicket);
    }

    @GetMapping("/tickets/{id}")
    Ticket one(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Could not find ticket " + id));
    }

    @PutMapping("/tickets/{id}")
    Ticket replaceTicket(@RequestBody Ticket newTicket, @PathVariable Long id) {
        return repository.findById(id).map(ticket -> {
            ticket.setMessage(newTicket.getMessage());
            return repository.save(ticket);
        })
                .orElseGet(() -> {
                    return repository.save(newTicket);
                });
    }
    
    @DeleteMapping("/tickets/{id}")
    void deleteTicket(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
