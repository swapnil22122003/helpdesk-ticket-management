package com.gtl.helpdesk.service;

import com.gtl.helpdesk.exception.TicketNotFoundException;
import com.gtl.helpdesk.model.Ticket;
import com.gtl.helpdesk.repository.TicketRepository;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    public TicketService(TicketRepository ticketRepository)
    {
        this.ticketRepository= ticketRepository;
    }

    public Ticket createTicket(Ticket ticket){
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket getTicketById( Long id) {

        return ticketRepository.findById(id)
        .orElseThrow(()->new TicketNotFoundException(id));
    }

    public Ticket updateTicket(Long id, Ticket updatedTicket){
        Ticket ticket= ticketRepository.findById(id).orElse(null);
        if(ticket==null){
            return null;
        }
        ticket.setTitle(updatedTicket.getTitle());
        ticket.setDescription(updatedTicket.getDescription());
        ticket.setStatus(updatedTicket.getStatus());
        ticket.setPriority(updatedTicket.getPriority());

        return ticketRepository.save(ticket);
    }

    public String deleteTicket(Long id){
        if (!ticketRepository.existsById(id)) {
            return "Ticket not Found";
        }

        ticketRepository.deleteById(id);

        return "Ticket deleted successfully";

        }
}
