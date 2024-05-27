package com.cloudbees.assessment.util;

import java.util.List;

public class Utils {

    public static int maxSeatsPerSection = 30;

    public static String generateTicketId() {
        return "" + System.currentTimeMillis();
    }

    public static String allocateSeatNumber(String section, List<String> allotedSeats) {
        String seatNumber = section + (int) (Math.random() * maxSeatsPerSection + 1);
        if (allotedSeats.contains(seatNumber)) {
            return allocateSeatNumber(section, allotedSeats);
        }
        return seatNumber;
    }
}
