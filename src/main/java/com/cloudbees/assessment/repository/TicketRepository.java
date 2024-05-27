package com.cloudbees.assessment.repository;

import com.cloudbees.assessment.dtos.Ticket;
import com.cloudbees.assessment.util.Utils;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TicketRepository {
    Map<String, Ticket> ticketMap = new HashMap<>();

    public void saveTicket(Ticket ticket) {
        System.out.println("Ticket saved");
        ticketMap.put(ticket.getTicketId(), ticket);
    }

    public Ticket getTicket(String ticketId) {
        System.out.println("Ticket retrieved");
        return ticketMap.get(ticketId);
    }

    public Map<String, Ticket> getTicketMap() {
        return ticketMap;
    }

    public void deleteTicket(String ticketId) {
        System.out.println("Ticket deleted");
        ticketMap.remove(ticketId);
    }

    public void updateTicket(Ticket ticket) {
        System.out.println("Ticket updated");
        ticketMap.put(ticket.getTicketId(), ticket);
    }
}
