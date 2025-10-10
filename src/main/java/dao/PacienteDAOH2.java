package dao;

import model.Domicilio;
import model.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAOH2 implements iDao<Paciente> {
    private static final String SQL_SELECT_ONE = " SELECT * FROM PACIENTES WHERE ID=?";
    private static final String SQL_SELECT_ALL = " SELECT * FROM PACIENTES";
    private static final String SQL_DELETE_ID = " DELETE FROM PACIENTES WHERE ID = ?";
    private static final String SQL_INSERT = "INSERT INTO PACIENTES(NOMBRE, APELLIDO, NUMEROCONTACTO, FECHAINGRESO, DOMICILIO_ID, EMAIL) VALUES(?,?,?,?,?,?);";
    private static final String SQL_UPDATE_ONE = "UPDATE PACIENTES SET NOMBRE = ?, APELLIDO = ?, NUMEROCONTACTO = ?, FECHAINGRESO = ?, DOMICILIO_ID = ?, EMAIL = ? WHERE ID = ?;";


    @Override
    public Paciente guardar(Paciente paciente) {
        Connection connection = null;
        try {
            connection = BD.getConnection();
            Statement statement = connection.createStatement();
            PreparedStatement ps_insert_one = connection.prepareStatement(SQL_INSERT);
            ps_insert_one.setString(1, paciente.getNombre());
            ps_insert_one.setString(2, paciente.getApellido());
            ps_insert_one.setInt(3, paciente.getNumeroContacto());
            ps_insert_one.setDate(4, Date.valueOf(paciente.getFechaIngreso()));
            ps_insert_one.setInt(5, paciente.getDomicilio().getId());
            ps_insert_one.setString(6, paciente.getEmail());
            int pacienteGuardado = ps_insert_one.executeUpdate();

            if (pacienteGuardado > 0) {
                System.out.println("El paciente se guardo.");
            } else {
                System.out.println("No se pudo guardar el paciente");
            }

        } catch (Exception e) {
            System.out.println("Error: "+ e.getMessage());
        }
        return paciente;
    }

    @Override
    public Paciente buscar(Integer id) {
        Connection connection = null;
        Paciente paciente = null;
        Domicilio domicilio = null;
        try {
            connection = BD.getConnection();
            //statement mundo java a sql
            Statement statement = connection.createStatement();
            PreparedStatement ps_select_one = connection.prepareStatement(SQL_SELECT_ONE);
            ps_select_one.setInt(1, id);
            //ResultSet mundo bdd a java
            ResultSet rs = ps_select_one.executeQuery();
            DomicilioDAOH2 daoAux = new DomicilioDAOH2();
            while (rs.next()) {
                domicilio = daoAux.buscar(rs.getInt(6));
                paciente = new Paciente(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5).toLocalDate(), domicilio, rs.getString(7));
            }
            System.out.println("paciente encontrado");

        } catch (Exception e) {
            e.getMessage();
        }
        return paciente;
    }

    @Override
    public void eliminar(Integer id) {
        Connection connection = null;
        try {
            connection = BD.getConnection();
            Statement statement = connection.createStatement();
            PreparedStatement ps_delete_one = connection.prepareStatement(SQL_DELETE_ID);
            ps_delete_one.setInt(1, id);
            int pacientesEliminado = ps_delete_one.executeUpdate();

            if (pacientesEliminado > 0) {
                System.out.println("EL paciente se elimino.");
            } else {
                System.out.println("No se encontro paciente con id: " + id);
            }
        } catch (Exception e) {
            System.out.println("Error: "+ e.getMessage());
        }
    }

    @Override
    public void actualizar(Paciente paciente) {
        Connection connection = null;
        try {
            connection = BD.getConnection();
            Statement statement = connection.createStatement();
            PreparedStatement ps_update_one = connection.prepareStatement(SQL_UPDATE_ONE);
            ps_update_one.setString(1, paciente.getNombre());
            ps_update_one.setString(2, paciente.getApellido());
            ps_update_one.setInt(3, paciente.getNumeroContacto());
            ps_update_one.setDate(4, Date.valueOf(paciente.getFechaIngreso()));
            ps_update_one.setInt(5, paciente.getDomicilio().getId());
            ps_update_one.setString(6, paciente.getEmail());
            ps_update_one.setInt(7, paciente.getId());
            int pacienteActualizado = ps_update_one.executeUpdate();

            if (pacienteActualizado > 0) {
                System.out.println("EL paciente se actualizo.");
            } else {
                System.out.println("No se encontro paciente con id: " + paciente.getId());
            }
        } catch (Exception e) {
            System.out.println("Error: "+ e.getMessage());
        }
    }

    @Override
    public Paciente buscarGenerico(String parametro) {
        return null;
    }

    @Override
    public List<Paciente> buscarTodos() {
        Connection connection = null;
        Paciente paciente = null;
        Domicilio domicilio = null;
        List<Paciente> pacientes = new ArrayList<>();;
        try {
            connection = BD.getConnection();
            Statement statement = connection.createStatement();
            PreparedStatement ps_select_all = connection.prepareStatement(SQL_SELECT_ALL);
            ResultSet rs = ps_select_all.executeQuery();
            DomicilioDAOH2 daoAux = new DomicilioDAOH2();
            while (rs.next()) {
                domicilio = daoAux.buscar(rs.getInt(6));
                paciente = new Paciente(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5).toLocalDate(), domicilio, rs.getString(7));
                pacientes.add(paciente);
            }
        } catch (Exception e) {
            System.out.println("Error: "+ e.getMessage());
        }
        return pacientes;
    }

}

