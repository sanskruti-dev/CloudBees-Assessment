package com.cloudbees.assessment.service;

import com.cloudbees.assessment.dtos.Ticket;
import com.cloudbees.assessment.models.TicketResponse;
import com.cloudbees.assessment.repository.TicketRepository;
import com.cloudbees.assessment.util.CloudbeesException;
import com.cloudbees.assessment.util.DataValidator;
import com.cloudbees.assessment.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public TicketResponse submitTicket(Ticket ticket) throws CloudbeesException {
        TicketResponse ticketResponse = new TicketResponse();
        DataValidator.validateTicketDetails(ticket);
        String ticketId = Utils.generateTicketId();
        ticket.setTicketId(ticketId);

        ticket.setSeatAllocted(allotAvailableSeatInSection(ticket.getSection()));

        ticketRepository.saveTicket(ticket);

        ticketResponse.setTicketId(ticketId);
        ticketResponse.setCode("200");
        ticketResponse.setMessage("Ticket submitted successfully");
        return ticketResponse;
    }

    public Ticket get(String ticketId) throws CloudbeesException {
        if (ticketId == null || ticketId.isEmpty()) {
            throw new CloudbeesException("Ticket id is required", 400);
        }
        Ticket ticket = ticketRepository.getTicket(ticketId);
        if (ticket == null) {
            throw new CloudbeesException("Ticket not found", 404);
        }
        return ticket;
    }

    public void delete(String ticketId) throws CloudbeesException {
        if (ticketId == null || ticketId.isEmpty()) {
            throw new CloudbeesException("Ticket id is required", 400);
        }
        Ticket ticketToDelete = ticketRepository.getTicket(ticketId);
        if (ticketToDelete == null) {
            throw new CloudbeesException("Ticket not found", 404);
        }
        ticketRepository.deleteTicket(ticketId);
    }

    public void update(String ticketId, Ticket ticket) throws CloudbeesException {
        if (ticketId == null || ticketId.isEmpty()) {
            throw new CloudbeesException("Ticket id is required", 400);
        }
        if(ticket.getSection() == null) {
            throw new CloudbeesException("Section is required", 400);
        }
        if(ticket.getSection() != null && !DataValidator.validSections.contains(ticket.getSection())) {
            throw new CloudbeesException("Invalid section, Section can be A or B", 400);
        }
        Ticket ticketToUpdate = ticketRepository.getTicket(ticketId);
        if (ticketToUpdate == null) {
            throw new CloudbeesException("Ticket not found", 404);
        }

        ticketToUpdate.setSection(ticket.getSection());
        ticketToUpdate.setSeatAllocted(allotAvailableSeatInSection(ticket.getSection()));

        ticketRepository.updateTicket(ticketToUpdate);
    }

    private String allotAvailableSeatInSection(String section) {
        if(remainingSeatsInSection(section) <= 0) {
            throw new CloudbeesException("No seats available in this section", 400);
        }
        List<String> allotedSeats = ticketRepository.getTicketMap().values().stream().map(Ticket::getSeatAllocted).toList();
        return Utils.allocateSeatNumber(section, allotedSeats);
    }

    private int remainingSeatsInSection(String section) {
        Map<String, Ticket> ticketMap = ticketRepository.getTicketMap();
        return Utils.maxSeatsPerSection -
                (int) ticketMap.values().stream().filter(ticket -> ticket.getSection().equals(section)).count();
    }

}
