package com.example.AplicacionEjercicioMVC.Repository;

import com.example.AplicacionEjercicioMVC.Models.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    Empleado findByEmail(String email);
}
