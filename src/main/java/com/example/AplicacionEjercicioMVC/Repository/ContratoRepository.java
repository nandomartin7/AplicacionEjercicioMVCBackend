package com.example.AplicacionEjercicioMVC.Repository;

import com.example.AplicacionEjercicioMVC.Models.Contrato;
import com.example.AplicacionEjercicioMVC.Models.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {
    Contrato findByCedula(String cedula);
}
