import dao.BD;
import dao.OdontologoDAOH2;
import model.Domicilio;
import model.Odontologo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.OdontologoService;

import java.time.LocalDate;
import java.util.List;

public class OdontologoTestService {

    OdontologoService odontologoService= new OdontologoService(new OdontologoDAOH2());
    
    @Test
    public void crearTablasConDatos(){
        //DADO
        BD.crearTablas();
    }


    @Test
    public void buscarOdontologo(){
        //DADO
        BD.crearTablas();
        //CUANDO
        Odontologo odontologo= odontologoService.buscarOdontologoPorId(1);
        //ENTONCES
        Assertions.assertTrue(odontologo!=null);
        System.out.println(odontologo.toString());
    }

    @Test
    void testBuscarOdontologos() {
        //DADO
        BD.crearTablas();
        //CUANDO
        List<Odontologo> odontologos= odontologoService.buscarOdontologos();
        //ENTONCES
        Assertions.assertEquals(1, odontologos.size());
        Assertions.assertEquals("Nick", odontologos.get(0).getNombre());
    }

    @Test
    void testEliminarOdontologo() {
        //DADO
        BD.crearTablas();
        int odontologosAntes= odontologoService.buscarOdontologos().size();
        //CUANDO
        odontologoService.eliminarOdontologo(1);
        List<Odontologo> odontologos= odontologoService.buscarOdontologos();
        //ENTONCES
        Assertions.assertEquals(odontologosAntes-1, odontologos.size());
    }

    @Test
    void testGuardarOdontologo() {
        //DADO
        BD.crearTablas();
        int odontologosAntes= odontologoService.buscarOdontologos().size();
        Odontologo bart = new Odontologo("Bart","Simpson","1651651");
        //CUANDO
        odontologoService.guardarOdontologo(bart);
        List<Odontologo> odontologos= odontologoService.buscarOdontologos();
        //ENTONCES
        Assertions.assertEquals(odontologosAntes+1, odontologos.size());
    }

    @Test
    void testActualizarOdontologo() {
        //DADO
        BD.crearTablas();
        Odontologo odontologo= odontologoService.buscarOdontologoPorId(1);
        String apellidoAnterior = odontologo.getApellido();
        odontologo.setApellido("Wolfe");
        //CUANDO
        odontologoService.actualizarOdontologo(odontologo);
        Odontologo odontologoActualizado= odontologoService.buscarOdontologoPorId(1);
        //ENTONCES
        Assertions.assertNotEquals(apellidoAnterior, odontologoActualizado.getApellido());
        System.out.println(apellidoAnterior +" - "+ odontologoActualizado.getApellido());
    }
}
