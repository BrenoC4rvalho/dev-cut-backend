package com.breno.devcut.exceptions;

public class AppointmentNotFoundException extends RuntimeException {
    public AppointmentNotFoundException() {
        super("Appointment not found.");
    }
}
