package com.cloudbees.assessment.models;

public class UserResponse {
    private String fullName;
    private String email;
    private String seatAllocated;
    private String ticketId;

    public UserResponse(String fullName, String email, String seatAllocated, String ticketId) {
        this.fullName = fullName;
        this.email = email;
        this.seatAllocated = seatAllocated;
        this.ticketId = ticketId;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSeatAllocated() {
        return seatAllocated;
    }

    public void setSeatAllocated(String seatAllocated) {
        this.seatAllocated = seatAllocated;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }
}
