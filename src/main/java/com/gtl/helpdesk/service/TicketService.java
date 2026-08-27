package com.gtl.helpdesk.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.gtl.helpdesk.exception.TicketNotFoundException;
import com.gtl.helpdesk.model.Ticket;
import com.gtl.helpdesk.repository.TicketRepository;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket createTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public Page<Ticket> getAllTickets(
            String sortBy,
            String direction,
            int page,
            int size) {

        Sort sort;

        if (direction.equalsIgnoreCase("desc")) {
            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy).ascending();
        }


        Pageable pageable = PageRequest.of(page, size, sort);

        return ticketRepository.findAll(pageable);
    }

    public Ticket getTicketById(Long id) {

        return ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException(id));
    }

    public Ticket updateTicket(Long id, Ticket updatedTicket) {
        Ticket ticket = ticketRepository.findById(id).orElse(null);
        if (ticket == null) {
            return null;
        }
        ticket.setTitle(updatedTicket.getTitle());
        ticket.setDescription(updatedTicket.getDescription());
        ticket.setStatus(updatedTicket.getStatus());
        ticket.setPriority(updatedTicket.getPriority());

        return ticketRepository.save(ticket);
    }

    public String deleteTicket(Long id) {
        if (!ticketRepository.existsById(id)) {
            return "Ticket not Found";
        }

        ticketRepository.deleteById(id);

        return "Ticket deleted successfully";

    }

    public List<Ticket> getTicketsByStatus(String status) {
        return ticketRepository.findByStatus(status);
    }

    public List<Ticket> getTicketsByPriority(String priority) {
        return ticketRepository.findByPriority(priority);
    }

    public List<Ticket> getTicketsByStatusAndPriority(String status, String priority) {
        return ticketRepository.findByStatusAndPriority(status, priority);
    }

    public Page<Ticket> getTicketsByStatusAndPriorityUsingRequest(
            String status,
            String priority,
            String sortBy,
            String direction,
            int page,
            int size) {

        Sort sort;

        if (direction.equalsIgnoreCase("desc")) {
            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy).ascending();
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        if (status != null && priority != null) {

            return ticketRepository.findByStatusAndPriority(
                    status, priority, pageable);

        } else if (status != null) {

            return ticketRepository.findByStatus(status, pageable);

        } else if (priority != null) {

            return ticketRepository.findByPriority(priority, pageable);

        } else {

            return ticketRepository.findAll(pageable);
        }
    }
}