package com.example.AplicacionEjercicioMVC.Service;

import com.example.AplicacionEjercicioMVC.Models.Contrato;
import com.example.AplicacionEjercicioMVC.Models.Empleado;
import com.example.AplicacionEjercicioMVC.Repository.ContratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ContratoService {
    @Autowired
    private final ContratoRepository repositorio;
    private final EmpleadoService empleadoService;

    public ContratoService(ContratoRepository repositorio, EmpleadoService empleadoService) {
        this.repositorio = repositorio;
        this.empleadoService = empleadoService;
    }

    private List<Contrato> contratos = new ArrayList<>();

    public List<Contrato> findAll(){
        return repositorio.findAll();
    }

    public Contrato registrarContrato(Contrato contrato){
        Empleado empleado = empleadoService.findByEmail(contrato.getEmpleado().getEmail());
        contrato.setEmpleado(empleado);
        contrato.setFechaIngreso(new Date());
        repositorio.save(contrato);
        return contrato;
    }

    public Contrato findByCedula(String cedula){
        return repositorio.findByCedula(cedula);
    }

    public Contrato update(String cedula, Contrato contrato){
        Contrato existeContrato = findByCedula(cedula);
        if(existeContrato != null){
            existeContrato.setCliente(contrato.getCliente());
            existeContrato.setCedula(contrato.getCedula());
            existeContrato.setDescripcion(contrato.getDescripcion());
            existeContrato.setValor(contrato.getValor());
            existeContrato.setEstado(contrato.getEstado());
            //Ajuste actualizacion fecha de Inicio
            LocalDate fechaInicio = contrato.getFechaInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            fechaInicio = fechaInicio.plusDays(1);
            existeContrato.setFechaInicio(Date.from(fechaInicio.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            //Ajuste actualizacion fecha de Fin
            LocalDate fechaFin = contrato.getFechaFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            fechaFin = fechaFin.plusDays(1);
            existeContrato.setFechaFin(Date.from(fechaFin.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            repositorio.save(existeContrato);
        }
        return existeContrato;
    }

    public Boolean delete (String cedula){
        Contrato existeContrato = findByCedula(cedula);
        if (existeContrato != null){
            repositorio.deleteById(existeContrato.getIdContrato());
            return true;
        }else {
            return false;
        }
    }
}
