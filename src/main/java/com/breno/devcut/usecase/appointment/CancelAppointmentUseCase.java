package com.breno.devcut.usecase.appointment;

import com.breno.devcut.enums.Status;
import com.breno.devcut.exceptions.AppointmentNotFoundException;
import com.breno.devcut.model.dto.appointment.ResponseAppointmentDTO;
import com.breno.devcut.model.entities.Appointment;
import com.breno.devcut.repository.AppointmentRepository;

import java.util.UUID;

public class CancelAppointmentUseCase {

    private final AppointmentRepository appointmentRepository;

    public CancelAppointmentUseCase(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public ResponseAppointmentDTO execute(UUID id) {
        Appointment existAppointment = appointmentRepository.findById(id)
                .orElseThrow(AppointmentNotFoundException::new);

        existAppointment.setStatus(Status.CANCELED);

        Appointment updateAppointment = appointmentRepository.save(existAppointment);

        return new ResponseAppointmentDTO(
                updateAppointment
        );
    }

}
