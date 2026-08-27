package com.gtl.helpdesk.controller;

import com.gtl.helpdesk.model.Ticket;
import com.gtl.helpdesk.service.TicketService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {


    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/status/{status}")
    public List<Ticket> getTicketsByStatus(@PathVariable String status) {
        return ticketService.getTicketsByStatus(status);
    }

    @GetMapping("/priority/{priority}")
    public List<Ticket> getTicketsByPriority(@PathVariable String priority) {
        return ticketService.getTicketsByPriority(priority);
    }

    @GetMapping("/status/{status}/priority/{priority}")
    public List<Ticket> getTicketsByStatusAndPriority(@PathVariable String status,@PathVariable String priority){
        return ticketService.getTicketsByStatusAndPriority(status,priority);
    }

    @GetMapping
    public Page<Ticket> getAllTickets(@RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "asc") String direction , @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size) {
        return ticketService.getAllTickets(sortBy, direction,page,size);
    }

    @GetMapping("/filter")
    public Page<Ticket> getTicketsByStatusAndPriorityUsingRequest(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size ) {

        return ticketService.getTicketsByStatusAndPriorityUsingRequest(status, priority, sortBy, direction , page , size);
    }

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        Ticket savedTicket = ticketService.createTicket(ticket);
        return ResponseEntity.status(201).body(savedTicket);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        Ticket ticket = ticketService.getTicketById(id);
        return ResponseEntity.ok(ticket);
    }

    @PutMapping("/{id}")
    public Ticket updateTicket(@PathVariable Long id, @RequestBody Ticket updatedTicket) {
        return ticketService.updateTicket(id,updatedTicket);
    }

    @DeleteMapping("/{id}")
    public String deleteTicket(@PathVariable Long id) {
        return ticketService.deleteTicket(id);
    }




}