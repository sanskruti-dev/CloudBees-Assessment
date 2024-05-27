package com.cloudbees.assessment.controllers;

import com.cloudbees.assessment.dtos.Ticket;
import com.cloudbees.assessment.models.TicketResponse;
import com.cloudbees.assessment.service.TicketService;
import com.cloudbees.assessment.util.CloudbeesException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TicketControllerTest {

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private TicketController ticketController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSubmitTicket() throws CloudbeesException {
        Ticket ticket = new Ticket();
        TicketResponse expectedResponse = new TicketResponse();
        expectedResponse.setCode("200");
        expectedResponse.setMessage("Ticket submitted successfully");

        when(ticketService.submitTicket(ticket)).thenReturn(expectedResponse);

        ResponseEntity<TicketResponse> responseEntity = ticketController.submitTicket(ticket);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    @Test
    public void testGetReceiptByTicketId() throws CloudbeesException {
        String ticketId = "123456";
        Ticket ticket = new Ticket();
        ticket.setTicketId(ticketId);

        when(ticketService.get(ticketId)).thenReturn(ticket);

        ResponseEntity<Object> responseEntity = ticketController.getReceiptByTicketId(ticketId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(ticket, responseEntity.getBody());
    }

    @Test
    public void testCancelTicketById() throws CloudbeesException {
        String ticketId = "123456";
        TicketResponse expectedResponse = new TicketResponse();
        expectedResponse.setCode("200");
        expectedResponse.setMessage("Ticket cancelled.");

        doNothing().when(ticketService).delete(ticketId);

        ResponseEntity<Object> responseEntity = ticketController.cancelTicketById(ticketId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateTicketById() throws CloudbeesException {
        String ticketId = "123456";
        Ticket ticket = new Ticket();
        TicketResponse expectedResponse = new TicketResponse();
        expectedResponse.setCode("200");
        expectedResponse.setMessage("New seat number allocated.");

        doNothing().when(ticketService).update(ticketId, ticket);

        ResponseEntity<Object> responseEntity = ticketController.updateTicketById(ticketId, ticket);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
