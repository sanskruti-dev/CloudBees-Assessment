package com.cloudbees.assessment.controllers;

import com.cloudbees.assessment.dtos.Ticket;
import com.cloudbees.assessment.models.TicketResponse;
import com.cloudbees.assessment.service.TicketService;
import com.cloudbees.assessment.util.CloudbeesException;
import com.cloudbees.assessment.util.URLConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(URLConstants.TICKET)
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("")
    public ResponseEntity<TicketResponse> submitTicket(@RequestBody Ticket ticket) {
        try {
            TicketResponse ticketResponse = ticketService.submitTicket(ticket);
            return ResponseEntity.ok().body(ticketResponse);
        } catch (CloudbeesException e) {
            return ResponseEntity.status(e.getHttpErrorCode()).body(prepareTicketResponseError(e));
        }
    }

    @GetMapping(URLConstants.TICKET_ID)
    public ResponseEntity<Object> getReceiptByTicketId(@PathVariable String ticketId) {
        try {
            Ticket ticket = ticketService.get(ticketId);
            return ResponseEntity.ok().body(ticket);
        } catch (CloudbeesException e) {
            return ResponseEntity.status(e.getHttpErrorCode()).body(prepareTicketResponseError(e));
        }
    }

    @DeleteMapping(URLConstants.TICKET_ID)
    public ResponseEntity<Object> cancelTicketById(@PathVariable String ticketId) {
        try {
            ticketService.delete(ticketId);
            TicketResponse ticketResponse = new TicketResponse();
            ticketResponse.setCode("200");
            ticketResponse.setMessage("Ticket cancelled.");
            return ResponseEntity.ok().body(ticketResponse);
        } catch (CloudbeesException e) {
            return ResponseEntity.status(e.getHttpErrorCode()).body(prepareTicketResponseError(e));
        }
    }

    @PutMapping(URLConstants.TICKET_ID)
    public ResponseEntity<Object> updateTicketById(@PathVariable String ticketId, @RequestBody Ticket ticket) {
        try {
            ticketService.update(ticketId, ticket);
            TicketResponse ticketResponse = new TicketResponse();
            ticketResponse.setCode("200");
            ticketResponse.setMessage("New seat number allocated.");
            return ResponseEntity.ok().body(ticketResponse);
        } catch (CloudbeesException e) {
            return ResponseEntity.status(e.getHttpErrorCode()).body(prepareTicketResponseError(e));
        }
    }

    public TicketResponse prepareTicketResponseError(CloudbeesException e) {
        TicketResponse errorResponse = new TicketResponse();
        errorResponse.setCode(String.valueOf(e.getHttpErrorCode()));
        errorResponse.setMessage(e.getMessage());
        return errorResponse;
    }
}
