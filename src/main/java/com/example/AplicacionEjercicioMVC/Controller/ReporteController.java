package com.example.AplicacionEjercicioMVC.Controller;

import com.example.AplicacionEjercicioMVC.Models.Contrato;
import com.example.AplicacionEjercicioMVC.Models.Empleado;
import com.example.AplicacionEjercicioMVC.Service.ContratoService;
import com.example.AplicacionEjercicioMVC.Service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reporte")
public class ReporteController {
    @Autowired
    private final ContratoService contratoService;
    @Autowired
    private final EmpleadoService empleadoService;


    public ReporteController(ContratoService contratoService, EmpleadoService empleadoService) {
        this.contratoService = contratoService;
        this.empleadoService = empleadoService;
    }

    @GetMapping("/filtrar")
    public Map<String, List<Contrato>> listarEmpleadosConContrato (
            @RequestParam("fechaInicial") @DateTimeFormat(pattern = "yyyy-MM-dd")Date fechaInicial,
            @RequestParam("fechaFin") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFinal){

        List<Contrato> contratosEnRango = contratoService.findAll().stream()
                .filter(contrato -> contrato.getFechaIngreso().compareTo(fechaInicial)>=0 && contrato.getFechaIngreso().compareTo(fechaFinal)<=0)
                .collect(Collectors.toList());

        Map<String, List<Contrato>> empleadosContratosMap = new HashMap<>();

        for (Contrato contrato : contratosEnRango) {
            Empleado empleado = contrato.getEmpleado();

            if (empleado != null) {
                // Crear una clave única para cada empleado (nombre + apellido + email)
                String empleadoKey = empleado.getNombre() + " " + empleado.getApellido() + " (" + empleado.getEmail() + ")";

                // Si ya existe una entrada para este empleado, añade el contrato a la lista
                empleadosContratosMap.putIfAbsent(empleadoKey, new ArrayList<>());
                empleadosContratosMap.get(empleadoKey).add(contrato);
            }
        }
        return empleadosContratosMap;
    }
}
