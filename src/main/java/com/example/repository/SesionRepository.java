package com.example.repository;

import com.example.model.Sesion;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SesionRepository extends JpaRepository<Sesion, Long> {
    List<Sesion> findByEstado(String estado);

    List<Sesion> findByFechaSesion(LocalDate fechaSesion);

    List<Sesion> findByNombreContainingIgnoreCase(String nombre);

}
