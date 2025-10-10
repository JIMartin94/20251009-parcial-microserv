import dao.BD;
import dao.PacienteDAOH2;
import model.Domicilio;
import model.Paciente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.PacienteService;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PacienteTestService {

    @Test
    public void crearTablasConDatos(){
        //DADO
        BD.crearTablas();
    }


    @Test
    public void buscarPaciente(){
        //DADO
        BD.crearTablas();
        PacienteService pacienteService= new PacienteService(new PacienteDAOH2());
        //CUANDO
        Paciente paciente= pacienteService.buscarPacientePorId(1);
        //ENTONCES
        Assertions.assertTrue(paciente!=null);
    }

    @Test
    void testBuscarPacientes() {
        //DADO
        BD.crearTablas();
        PacienteService pacienteService= new PacienteService(new PacienteDAOH2());
        //CUANDO
        List<Paciente> pacientes= pacienteService.buscarPacientes();
        //ENTONCES
        Assertions.assertEquals(2, pacientes.size());
        Assertions.assertEquals("Marge", pacientes.get(1).getNombre());
    }

    @Test
    void testEliminarPaciente() {
        //DADO
        BD.crearTablas();
        PacienteService pacienteService= new PacienteService(new PacienteDAOH2());
        int pacientesAntes= pacienteService.buscarPacientes().size();
        //CUANDO
        pacienteService.eliminarPaciente(1);
        List<Paciente> pacientes= pacienteService.buscarPacientes();
        //ENTONCES
        Assertions.assertEquals(pacientesAntes-1, pacientes.size());
    }

    @Test
    void testGuardarPaciente() {
        //DADO
        BD.crearTablas();
        PacienteService pacienteService= new PacienteService(new PacienteDAOH2());
        int pacientesAntes= pacienteService.buscarPacientes().size();
        Domicilio bartCasa = new Domicilio(1,"siempre viva",723,"Springfield","USA");
        Paciente bart = new Paciente("Bart","Simpson",5, LocalDate.of(2025,10,10),bartCasa,"bart@disney.com");
        //CUANDO
        pacienteService.guardarPaciente(bart);
        List<Paciente> pacientes= pacienteService.buscarPacientes();
        //ENTONCES
        Assertions.assertEquals(pacientesAntes+1, pacientes.size());
    }

    @Test
    void testActualizarPaciente() {
        //DADO
        BD.crearTablas();
        PacienteService pacienteService= new PacienteService(new PacienteDAOH2());
        Paciente paciente= pacienteService.buscarPacientePorId(1);
        String nombreAnterior = paciente.getNombre();
        paciente.setNombre("Homero Jay");
        //CUANDO
        pacienteService.actualizarPaciente(paciente);
        Paciente pacienteActualizado= pacienteService.buscarPacientePorId(1);
        //ENTONCES
        Assertions.assertNotEquals(nombreAnterior, pacienteActualizado.getNombre());
        System.out.println(nombreAnterior +" - "+ pacienteActualizado.getNombre());
    }
}
