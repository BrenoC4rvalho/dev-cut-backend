package com.breno.devcut.usecase.appointment;

import com.breno.devcut.enums.Status;
import com.breno.devcut.exceptions.AppointmentNotFoundException;
import com.breno.devcut.model.dto.appointment.ResponseAppointmentDTO;
import com.breno.devcut.model.entities.Appointment;
import com.breno.devcut.repository.AppointmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CancelAppointmentUseCaseTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private CancelAppointmentUseCase cancelAppointmentUseCase;

    @Test
    void should_cancel_appointment_successfully() {
        UUID appointmentId = UUID.randomUUID();
        Appointment existingAppointment = new Appointment();
        existingAppointment.setId(appointmentId);
        existingAppointment.setStatus(Status.CONFIRMED);

        when(appointmentRepository.findById(appointmentId))
                .thenReturn(Optional.of(existingAppointment));
        when(appointmentRepository.save(any(Appointment.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ResponseAppointmentDTO result = cancelAppointmentUseCase.execute(appointmentId);

        assertEquals(Status.CANCELED, result.getStatus());
        verify(appointmentRepository).save(existingAppointment);
        assertEquals(Status.CANCELED, existingAppointment.getStatus());
    }

    @Test
    void should_throw_exception_when_AppointmentNotFound() {
        UUID invalidAppointmentId = UUID.randomUUID();

        when(appointmentRepository.findById(invalidAppointmentId))
                .thenReturn(Optional.empty());

        assertThrows(AppointmentNotFoundException.class, () -> {
            cancelAppointmentUseCase.execute(invalidAppointmentId);
        });

        verify(appointmentRepository, never()).save(any());
    }
}