package com.breno.devcut.usecase.appointment;

import com.breno.devcut.exceptions.AppointmentNotFoundException;
import com.breno.devcut.model.dto.appointment.ResponseAppointmentDTO;
import com.breno.devcut.model.entities.Appointment;
import com.breno.devcut.repository.AppointmentRepository;

import java.util.UUID;

public class FindAppointmentByIdUseCase {

    private final AppointmentRepository appointmentRepository;

    public FindAppointmentByIdUseCase(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public ResponseAppointmentDTO execute(UUID id) {

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(AppointmentNotFoundException::new);

        return new ResponseAppointmentDTO(
            appointment
        );

    }

}
