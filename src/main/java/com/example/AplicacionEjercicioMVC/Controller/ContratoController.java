package com.example.AplicacionEjercicioMVC.Controller;

import com.example.AplicacionEjercicioMVC.Models.Contrato;
import com.example.AplicacionEjercicioMVC.Models.Empleado;
import com.example.AplicacionEjercicioMVC.Service.ContratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contratos")
public class ContratoController {
    @Autowired
    private final ContratoService contratoService;

    public ContratoController(ContratoService contratoService) {
        this.contratoService = contratoService;
    }


    @GetMapping("/info")
    public String index(){
        return "Conectado a la tabla Contratos";
    }

    @GetMapping()
    public List<Contrato> getAllContratos(){
        return contratoService.findAll();
    }

    @GetMapping("/{cedula}")
    public ResponseEntity<Contrato> getContratoByCedula(@PathVariable String cedula){
        Contrato contrato = contratoService.findByCedula(cedula);
        return contrato != null ? ResponseEntity.ok(contrato) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Contrato> createContrato(@RequestBody Contrato contrato){
        return new ResponseEntity<>(contratoService.registrarContrato(contrato), HttpStatus.CREATED);
    }

    @PutMapping("/{cedula}")
    public ResponseEntity<Contrato> updateContrato(@PathVariable String cedula, @RequestBody Contrato contrato){
        Contrato actualizado = contratoService.update(cedula,contrato);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{cedula}")
    public ResponseEntity<Void> deleteContrato (@PathVariable String cedula){
        return contratoService.delete(cedula) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
