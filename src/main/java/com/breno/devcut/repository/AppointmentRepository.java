package com.breno.devcut.repository;

import com.breno.devcut.model.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {


}
