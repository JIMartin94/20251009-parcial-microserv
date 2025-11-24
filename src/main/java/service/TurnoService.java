package com.clinicaODontologica.UP.service;

import com.clinicaODontologica.UP.dto.TurnoDTO;
import com.clinicaODontologica.UP.entity.Turno;
import com.clinicaODontologica.UP.repository.OdontologoRepository;
import com.clinicaODontologica.UP.repository.PacienteRepository;
import com.clinicaODontologica.UP.repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TurnoService {
   @Autowired
    private TurnoRepository turnoRepository;

       public TurnoDTO guardarTurno(Turno turno){
           Turno turnoGuardado= turnoRepository.save(turno);
        return turnoATurnoDTO(turnoGuardado);
    }
    public List<TurnoDTO> listarTurnos(){
           List<Turno> turnoList= turnoRepository.findAll();
           List<TurnoDTO> listaTurnoDTO= new ArrayList<>();
        for (Turno turno : turnoList) {
            listaTurnoDTO.add(turnoATurnoDTO(turno));
                    }
        return listaTurnoDTO;
    }
    public TurnoDTO turnoATurnoDTO(Turno turno){
           TurnoDTO turnoDTO= new TurnoDTO();
           turnoDTO.setId(turno.getId());
           turnoDTO.setPacienteId(turno.getPaciente().getId());
           turnoDTO.setOdontologoId(turno.getOdontologo().getId());
           turnoDTO.setFecha(turno.getFecha());
           return turnoDTO;
    }

}
