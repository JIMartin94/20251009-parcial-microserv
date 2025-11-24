package com.clinicaODontologica.UP.Controller;

import com.clinicaODontologica.UP.Exception.ResourceNotFoundException;
import com.clinicaODontologica.UP.entity.Paciente;
import com.clinicaODontologica.UP.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController //Sin tecnologia de vista
@RequestMapping("/paciente") //todo lo que venga con endpoint pacinete
public class PacienteController {
    //Quien representa el modelo DAO?
    private PacienteService pacienteService;
    @Autowired
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    public ResponseEntity<Paciente> registrarPaciente(@RequestBody Paciente paciente){
        Optional<Paciente> pacienteBuscado= pacienteService.buscarPorEmail(paciente.getEmail());
        if(pacienteBuscado.isPresent()){
            return ResponseEntity.badRequest().build();
        }else{
            return ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
        }

    }@GetMapping("/{email}")
    public ResponseEntity<Optional<Paciente>> buscarPorEmail(@PathVariable String email) throws ResourceNotFoundException{
        Optional<Paciente> pacienteBuscado=pacienteService.buscarPorEmail(email);
        if(pacienteBuscado.isPresent()){
            return ResponseEntity.ok(pacienteBuscado);
        }
        else{
            throw new ResourceNotFoundException("paciente no encontrado");
        }

    }

}
