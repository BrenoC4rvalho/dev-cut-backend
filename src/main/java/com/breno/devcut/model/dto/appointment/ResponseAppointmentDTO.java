package com.breno.devcut.model.dto.appointment;

import com.breno.devcut.enums.Status;
import com.breno.devcut.model.entities.Appointment;
import com.breno.devcut.model.entities.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class ResponseAppointmentDTO {

    private UUID id;
    private User client;
    private LocalDateTime dateTime;
    private Status status;
    private String notes;

    public ResponseAppointmentDTO(UUID id, User client, LocalDateTime dateTime, Status status, String notes) {
        this.id = id;
        this.client = client;
        this.dateTime = dateTime;
        this.status = status;
        this.notes = notes;
    }

    public ResponseAppointmentDTO() {
    }

    public ResponseAppointmentDTO(Appointment updateAppointment) {
        this.id = updateAppointment.getId();
        this.client = updateAppointment.getClient();
        this.dateTime = updateAppointment.getDateTime();
        this.status = updateAppointment.getStatus();
        this.notes = updateAppointment.getNotes();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public LocalDateTime getDatetime() {
        return dateTime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.dateTime = datetime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
