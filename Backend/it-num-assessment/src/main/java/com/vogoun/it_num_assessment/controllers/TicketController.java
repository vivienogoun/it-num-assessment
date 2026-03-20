package com.vogoun.it_num_assessment.controllers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vogoun.it_num_assessment.models.Ticket;
import com.vogoun.it_num_assessment.models.User;
import com.vogoun.it_num_assessment.repositories.TicketRepository;
import com.vogoun.it_num_assessment.repositories.UserRepository;

@RestController
@RequestMapping("/api")
public class TicketController {
    private final TicketRepository repository;
    private final UserRepository userRepository;

    TicketController(TicketRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    private User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) auth.getPrincipal()).getUsername();
        User user = userRepository.findByUsername(username);
        return user;
    }

    @GetMapping("/tickets")
    Set<Map<String, Object>> all() {
        User user = this.getUser();
        Set<Map<String, Object>> result = new HashSet<>();
        user.getTickets().forEach(ticket -> {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("id", ticket.getId());
            item.put("message", ticket.getMessage());
            result.add(item);
        });
        return result;
    }

    @PostMapping("/tickets")
    Map<String, Object> newTicket(@RequestBody Ticket newTicket) {
        User user = this.getUser();
        Ticket t = new Ticket();
        t.setMessage(newTicket.getMessage());
        t.setUser(user);
        Ticket saved = repository.save(t);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("id", saved.getId());
        result.put("message", saved.getMessage());
        return result;
    }

    @GetMapping("/tickets/{id}")
    Map<String, Object> one(@PathVariable Long id) {
        User user = this.getUser();
        Ticket ticket = repository.findById(id).orElseThrow(() -> new RuntimeException("Could not find ticket " + id));
        if (ticket.getUser() != user) {
            throw new RuntimeException("Forbidden");
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("id", ticket.getId());
        result.put("message", ticket.getMessage());
        return result;
    }

    @PutMapping("/tickets/{id}")
    Map<String, Object> replaceTicket(@RequestBody Ticket newTicket, @PathVariable Long id) {
        User user = this.getUser();
        Ticket ticket = repository.findById(id).orElseThrow(() -> new RuntimeException("Could not find ticket " + id));
        if (ticket.getUser() != user) {
            throw new RuntimeException("Forbidden");
        }
        ticket.setMessage(newTicket.getMessage());
        Ticket saved = repository.save(ticket);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("id", saved.getId());
        result.put("message", saved.getMessage());
        return result;
    }

    @DeleteMapping("/tickets/{id}")
    void deleteTicket(@PathVariable Long id) {
        User user = this.getUser();
        Ticket ticket = repository.findById(id).orElseThrow(() -> new RuntimeException("Could not find ticket " + id));
        if (ticket.getUser() != user) {
            throw new RuntimeException("Forbidden");
        }
        repository.deleteById(id);
    }
}
