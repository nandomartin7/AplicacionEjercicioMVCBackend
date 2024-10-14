package com.example.AplicacionEjercicioMVC.Controller;

import com.example.AplicacionEjercicioMVC.Models.Contrato;
import com.example.AplicacionEjercicioMVC.Models.Empleado;
import com.example.AplicacionEjercicioMVC.Service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class EmpleadoController {
    @Autowired
    private final EmpleadoService empleadoService;
    private final PasswordEncoder passwordEncoder;

    public EmpleadoController(EmpleadoService  empleadoService, PasswordEncoder passwordEncoder) {
        this.empleadoService = empleadoService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/info")
    public String index(){
        return "Conectado a la tabla Empleados";
    }

    @GetMapping("")
    public List<Empleado> getAllEmpleados(){
        return empleadoService.findAll();
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Empleado empleado){
        Empleado existe = empleadoService.findByEmail(empleado.getEmail());
        if (existe != null && passwordEncoder.matches(empleado.getPassword(), existe.getPassword())){
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Inicio de sesión exitoso");
            response.put("empleado", existe);

            return ResponseEntity.ok(response);
        }

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message","Credenciales inválidas, el correo ya está registrado");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @GetMapping("/login")
    public ResponseEntity<String> loginPage(){
        return ResponseEntity.ok("Pagina de inicio de sesion");
    }

    @PostMapping("/registro")
    public ResponseEntity<String> registrar(@RequestBody Empleado empleado){
        String encodedPaassword = passwordEncoder.encode(empleado.getPassword());
        empleadoService.registrarEmpleado(empleado, encodedPaassword);
        return ResponseEntity.ok("Registro Exitoso");
    }
}
