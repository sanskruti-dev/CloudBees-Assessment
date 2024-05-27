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
}
