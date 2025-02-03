package com.breno.devcut.usecase.appointment;

import com.breno.devcut.enums.Status;
import com.breno.devcut.exceptions.AppointmentNotFoundException;
import com.breno.devcut.model.dto.appointment.ResponseAppointmentDTO;
import com.breno.devcut.model.entities.Appointment;
import com.breno.devcut.model.entities.User;
import com.breno.devcut.repository.AppointmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindAppointmentByIdUseCaseTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private FindAppointmentByIdUseCase findAppointmentByIdUseCase;


    @Test
    void should_return_appointment_when_id_exists() {
        UUID appointmentId = UUID.randomUUID();
        User user = new User();
        Appointment appointment = new Appointment(appointmentId, user, LocalDateTime.now(), Status.SCHEDULED, "Test Notes");

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appointment));

        ResponseAppointmentDTO result = findAppointmentByIdUseCase.execute(appointmentId);

        assertNotNull(result);
        assertEquals(appointmentId, result.getId());
        assertEquals(appointment.getStatus(), result.getStatus());
        verify(appointmentRepository, times(1)).findById(appointmentId);
    }

    @Test
    void should_throw_exceptionWhenAppointmentNotFound() {
        UUID appointmentId = UUID.randomUUID();

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.empty());

        assertThrows(AppointmentNotFoundException.class, () -> findAppointmentByIdUseCase.execute(appointmentId));

        verify(appointmentRepository, times(1)).findById(appointmentId);
    }
}
