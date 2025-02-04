package com.breno.devcut.usecase.appointment;

import com.breno.devcut.enums.Status;
import com.breno.devcut.exceptions.UserNotFoundException;
import com.breno.devcut.model.dto.appointment.CreateAppointmentDTO;
import com.breno.devcut.model.dto.appointment.ResponseAppointmentDTO;
import com.breno.devcut.model.entities.Appointment;
import com.breno.devcut.model.entities.User;
import com.breno.devcut.repository.AppointmentRepository;
import com.breno.devcut.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateAppointmentUseCaseTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CreateAppointmentUseCase createAppointmentUseCase;

    @Test
    void should_create_appointment_successfully() {

        UUID clientId = UUID.randomUUID();
        User client = new User();
        client.setId(clientId);

        //enter a valid time, between 8 and 18
        LocalDateTime validDateTime = LocalDateTime.now().plusHours(12);
        CreateAppointmentDTO validDto = new CreateAppointmentDTO(
                validDateTime,
                "Test"
        );

        when(userRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(appointmentRepository.existsByDateTime(validDateTime)).thenReturn(false);
        when(appointmentRepository.save(any(Appointment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ResponseAppointmentDTO result = createAppointmentUseCase.execute(validDto, clientId);

        assertNotNull(result);
        assertEquals(validDateTime, result.getDatetime());
        assertEquals("Test", result.getNotes());
        assertEquals(Status.SCHEDULED, result.getStatus());

        verify(appointmentRepository).save(any(Appointment.class));
    }

    @Test
    void should_throw_when_time_slot_is_taken() {

        LocalDateTime conflictingTime = LocalDateTime.now().plusHours(3);
        CreateAppointmentDTO dto = new CreateAppointmentDTO(conflictingTime, "Test");

        when(appointmentRepository.existsByDateTime(conflictingTime)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () ->{
            createAppointmentUseCase.execute(dto, UUID.randomUUID());
        });
    }

    @Test
    void should_throw_when_dateTime_is_in_past() {
        LocalDateTime pastTime = LocalDateTime.now().minusMinutes(30);
        CreateAppointmentDTO dto = new CreateAppointmentDTO(pastTime, "Test");

        assertThrows(IllegalArgumentException.class, () ->{
            createAppointmentUseCase.execute(dto, UUID.randomUUID());
        });
    }

    @Test
    void should_throw_when_outside_business_hours() {
        LocalDateTime earlyTime = LocalDateTime.now()
                .plusDays(1)
                .with(LocalTime.of(7, 30));

        CreateAppointmentDTO dto = new CreateAppointmentDTO(earlyTime, "Test");

        assertThrows(IllegalArgumentException.class, () ->{
            createAppointmentUseCase.execute(dto, UUID.randomUUID());
        });
    }

    @Test
    void should_throw_when_user_not_found() {

        // enter a valid time, between 8 and 18
        LocalDateTime validTime = LocalDateTime.now().plusHours(2);
        CreateAppointmentDTO dto = new CreateAppointmentDTO(validTime, "Test");

        UUID clientId = UUID.randomUUID();

        when(userRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () ->{
            createAppointmentUseCase.execute(dto, clientId);
        });
    }

}