package com.example.AplicacionEjercicioMVC.Service;

import com.example.AplicacionEjercicioMVC.Models.Empleado;
import com.example.AplicacionEjercicioMVC.Repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService {
    @Autowired
    private final EmpleadoRepository repositorio;

    public EmpleadoService(EmpleadoRepository repositorio) {
        this.repositorio = repositorio;
    }

    public List<Empleado> findAll(){
        return repositorio.findAll();
    }

    public Empleado registrarEmpleado(Empleado empleado, String encodedPassword){
        empleado.setPassword(encodedPassword);
        empleado.setActivo(true);
        empleado.setRol("Empleado");
        repositorio.save(empleado);
        return empleado;
    }

    public Empleado findByEmail(String email){
        return repositorio.findByEmail(email);
    }
}
