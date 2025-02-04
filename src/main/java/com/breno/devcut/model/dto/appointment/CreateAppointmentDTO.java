package com.breno.devcut.model.dto.appointment;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public class CreateAppointmentDTO {


    @NotNull(message = "The appointment date cannot be null.")
    private LocalDateTime dateTime;

    @Length(max = 200, message = "The notes field cannot be greater than 200.")
    private String notes;

    public CreateAppointmentDTO(LocalDateTime dateTime, String notes) {
        this.dateTime = dateTime;
        this.notes = notes;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
