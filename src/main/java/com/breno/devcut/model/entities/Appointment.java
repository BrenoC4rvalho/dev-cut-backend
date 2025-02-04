package com.breno.devcut.model.entities;

import com.breno.devcut.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@DynamicInsert
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private User client;

    @NotNull(message = "The appointment date cannot be null.")
    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    private Status status = Status.SCHEDULED;

    @Length(max = 200, message = "The notes field cannot be greater than 200.")
    private String notes;

    public Appointment(UUID id, User client, LocalDateTime dateTime, Status status, String notes) {
        this.id = id;
        this.client = client;
        this.dateTime = dateTime;
        this.status = status;
        this.notes = notes;
    }

    public Appointment() {
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
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
