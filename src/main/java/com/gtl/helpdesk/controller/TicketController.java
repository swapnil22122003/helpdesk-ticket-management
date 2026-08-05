package com.gtl.helpdesk.controller;

import com.gtl.helpdesk.model.Ticket;
import com.gtl.helpdesk.repository.TicketRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketRepository ticketRepository;

    public TicketController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @PostMapping
    public Ticket createTicket(@RequestBody Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    @GetMapping("/{id}")
    public Ticket getTicketById(@PathVariable Long id) {
        return ticketRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Ticket updateTicket(@PathVariable Long id, @RequestBody Ticket updatedTicket) {

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

    @DeleteMapping("/{id}")
    public String deleteTicket(@PathVariable Long id) {

        if (!ticketRepository.existsById(id)) {
            return "Ticket not found";
        }

        ticketRepository.deleteById(id);

        return "Ticket deleted successfully";
    }
}