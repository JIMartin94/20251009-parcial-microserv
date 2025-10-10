import dao.BD;
import dao.PacienteDAOH2;
import model.Domicilio;
import model.Paciente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.PacienteService;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PacienteTestService {
    PacienteService pacienteService= new PacienteService(new PacienteDAOH2());

    @BeforeEach
    public void crearTablas() {
        System.out.println("Creando tablas");
        BD.crearTablas();
    }


    @Test
    public void buscarPaciente(){
        System.out.println("Metodo buscarPaciente()");
        Paciente paciente= pacienteService.buscarPacientePorId(1);
        Assertions.assertTrue(paciente!=null);
    }

    @Test
    void testBuscarPacientes() {
        System.out.println("Metodo testBuscarPacientes()");
        List<Paciente> pacientes= pacienteService.buscarPacientes();
        Assertions.assertEquals(2, pacientes.size());
        Assertions.assertEquals("Marge", pacientes.get(1).getNombre());
    }

    @Test
    void testEliminarPaciente() {
        System.out.println("Metodo testEliminarPaciente()");
        int pacientesAntes= pacienteService.buscarPacientes().size();
        //CUANDO
        pacienteService.eliminarPaciente(1);
        List<Paciente> pacientes= pacienteService.buscarPacientes();
        //ENTONCES
        Assertions.assertEquals(pacientesAntes-1, pacientes.size());
    }

    @Test
    void testGuardarPaciente() {
        System.out.println("Metodo testGuardarPaciente()");
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
        System.out.println("Metodo testActualizarPaciente()");
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

    @Test
    void testBuscarPacientesPorString() {
        System.out.println("Metodo testBuscarPacientesPorString()");
        List<Paciente> pacientes= pacienteService.buscarPacientePorString("Marg");
        Assertions.assertEquals("Marge", pacientes.get(0).getNombre());
        Assertions.assertTrue(pacientes!=null);
    }
}
