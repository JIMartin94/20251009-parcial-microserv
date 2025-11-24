package com.clinicaODontologica.UP.service;

import com.clinicaODontologica.UP.entity.Odontologo;
import com.clinicaODontologica.UP.repository.OdontologoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OdontologoService {
    @Autowired
    private OdontologoRepository odontologoRepository;

    public Odontologo registrarOdontologo(Odontologo odontologo){
        return odontologoRepository.save(odontologo);
    }
    public Optional<Odontologo> buscarPorId(Long id){
        return odontologoRepository.findById(id);
    }
}
