package com.clinicaODontologica.UP.Controller;

import com.clinicaODontologica.UP.dto.TurnoDTO;
import com.clinicaODontologica.UP.entity.Odontologo;
import com.clinicaODontologica.UP.entity.Paciente;
import com.clinicaODontologica.UP.entity.Turno;
import com.clinicaODontologica.UP.service.OdontologoService;
import com.clinicaODontologica.UP.service.PacienteService;
import com.clinicaODontologica.UP.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turno")
public class TurnoController {
    private OdontologoService odontologoService;
    private PacienteService pacienteService;
    private TurnoService turnoService;

    @Autowired
    public TurnoController(OdontologoService odontologoService, PacienteService pacienteService, TurnoService turnoService) {
        this.odontologoService = odontologoService;
        this.pacienteService = pacienteService;
        this.turnoService = turnoService;
    }
    @PostMapping
    public ResponseEntity<TurnoDTO> registrarTurno(@RequestBody Turno turno){
        Optional<Paciente> pacienteBuscado= pacienteService.buscarPorId(turno.getPaciente().getId());
        Optional<Odontologo> odontologoBuscado= odontologoService.buscarPorId(turno.getOdontologo().getId());
        if(pacienteBuscado.isPresent()&& odontologoBuscado.isPresent()){
            return ResponseEntity.ok(turnoService.guardarTurno(turno));
        }else{
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping
    public ResponseEntity<List<TurnoDTO>> listarTurnosDTO(){
        return ResponseEntity.ok(turnoService.listarTurnos());
    }

}
