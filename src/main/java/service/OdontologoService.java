package service;

import dao.iDao;
import model.Odontologo;
import model.Paciente;


import java.util.List;

public class OdontologoService {
    private iDao<Odontologo> odontologoiDao;

    public OdontologoService(iDao<Odontologo> odontologoiDao) {
        this.odontologoiDao = odontologoiDao;
    }

    public Odontologo guardarOdontologo(Odontologo odontologo){
        return odontologoiDao.guardar(odontologo);
    }
    public Odontologo buscarOdontologoPorId(Integer id){
        return odontologoiDao.buscar(id);
    }
    public List<Odontologo> buscarOdontologos(){
        return odontologoiDao.buscarTodos();
    }
    public void eliminarOdontologo(Integer id) {
        odontologoiDao.eliminar(id);
    }
    public void actualizarOdontologo(Odontologo odontologo) {
        odontologoiDao.actualizar(odontologo);
    }
    public List<Odontologo> buscarOdontologoPorString(String texto) { return odontologoiDao.buscarPorString(texto);}

}
