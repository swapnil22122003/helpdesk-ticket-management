package com.gtl.helpdesk.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.gtl.helpdesk.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    // Without sorting
    List<Ticket> findByStatus(String status);

    List<Ticket> findByPriority(String priority);

    List<Ticket> findByStatusAndPriority(String status, String priority);

    // With sorting
    List<Ticket> findByStatus(String status, Sort sort);

    List<Ticket> findByPriority(String priority, Sort sort);

    List<Ticket> findByStatusAndPriority(String status,String priority,Sort sort
    );

    // with paging
    Page<Ticket> findByStatus(String status, Pageable pageable);

    Page<Ticket> findByPriority(String priority, Pageable pageable);

    Page<Ticket> findByStatusAndPriority(
            String status,
            String priority,
            Pageable pageable
    );

}