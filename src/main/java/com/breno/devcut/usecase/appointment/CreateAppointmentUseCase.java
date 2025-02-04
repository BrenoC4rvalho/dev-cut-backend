package com.breno.devcut.usecase.appointment;

import com.breno.devcut.enums.Status;
import com.breno.devcut.exceptions.UserNotFoundException;
import com.breno.devcut.model.dto.appointment.CreateAppointmentDTO;
import com.breno.devcut.model.dto.appointment.ResponseAppointmentDTO;
import com.breno.devcut.model.entities.Appointment;
import com.breno.devcut.model.entities.User;
import com.breno.devcut.repository.AppointmentRepository;
import com.breno.devcut.repository.UserRepository;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public class CreateAppointmentUseCase {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    public CreateAppointmentUseCase(AppointmentRepository appointmentRepository, UserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
    }

    public ResponseAppointmentDTO execute(CreateAppointmentDTO dto, UUID clientId) {

        LocalDateTime appointmentDateTime;
        try {
            appointmentDateTime = dto.getDateTime();
        } catch (DateTimeException e) {
            throw  new IllegalArgumentException("Invalid datetime format. Expected ISO-8601 format with timezone.");
        }

        boolean dateTimeAlreadyAppointment = appointmentRepository.existsByDateTime(appointmentDateTime);
        if(dateTimeAlreadyAppointment) {
            throw new IllegalArgumentException("Already have an appointment at that time.");
        }

        LocalDateTime now = LocalDateTime.now();
        if(!appointmentDateTime.isAfter(now)) {
            throw new IllegalArgumentException("Appointment datetime must be in the future.");
        }

        LocalTime appointmentTime = appointmentDateTime.toLocalTime();
        LocalTime startBusiness = LocalTime.of(8, 0);
        LocalTime endBusiness = LocalTime.of(18, 0);

        if(appointmentTime.isBefore(startBusiness) || appointmentTime.isAfter(endBusiness)) {
            throw new IllegalArgumentException("Appointment must be within business hours (08:00–18:00).");
        }

        User client = userRepository.findById(clientId)
                .orElseThrow(UserNotFoundException::new);

        Appointment appointment = new Appointment();
        appointment.setClient(client);
        appointment.setDateTime(appointmentDateTime);
        appointment.setStatus(Status.SCHEDULED);
        appointment.setNotes(dto.getNotes());

        Appointment newAppointment = appointmentRepository.save(appointment);

        return new ResponseAppointmentDTO(newAppointment);
    }
}
