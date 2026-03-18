package com.vogoun.it_num_assessment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vogoun.it_num_assessment.models.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    
}
