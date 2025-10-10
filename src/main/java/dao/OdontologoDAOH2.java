package dao;

import model.Domicilio;
import model.Odontologo;
import model.Odontologo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OdontologoDAOH2 implements iDao<Odontologo> {
    private static final String SQL_SELECT_ONE = " SELECT * FROM ODONTOLOGOS WHERE ID=?";
    private static final String SQL_SELECT_ALL = " SELECT * FROM ODONTOLOGOS";
    private static final String SQL_DELETE_ID = " DELETE FROM ODONTOLOGOS WHERE ID = ?";
    private static final String SQL_INSERT = "INSERT INTO ODONTOLOGOS(NOMBRE, APELLIDO, MATRICULA) VALUES(?,?,?);";
    private static final String SQL_UPDATE_ONE = "UPDATE ODONTOLOGOS SET NOMBRE = ?, APELLIDO = ?, MATRICULA = ? WHERE ID = ?;";
    private static final String SQL_SELECT_STRING = "SELECT * FROM ODONTOLOGOS WHERE UPPER(NOMBRE) LIKE UPPER(?) OR UPPER(APELLIDO) LIKE UPPER(?)";

    @Override
    public Odontologo guardar(Odontologo odontologo) {
        Connection connection = null;
        try {
            connection = BD.getConnection();
            Statement statement = connection.createStatement();
            PreparedStatement ps_insert_one = connection.prepareStatement(SQL_INSERT);
            ps_insert_one.setString(1, odontologo.getNombre());
            ps_insert_one.setString(2, odontologo.getApellido());
            ps_insert_one.setString(3, odontologo.getMatricula());
            int odontologoGuardado = ps_insert_one.executeUpdate();

            if (odontologoGuardado > 0) {
                System.out.println("El odontologo se guardo.");
            } else {
                System.out.println("No se pudo guardar el odontologo");
            }

        } catch (Exception e) {
            System.out.println("Error: "+ e.getMessage());
        }
        return odontologo;
    }

    @Override
    public Odontologo buscar(Integer id) {
        Connection connection = null;
        Odontologo odontologo = null;
        try {
            connection = BD.getConnection();
            //statement mundo java a sql
            Statement statement = connection.createStatement();
            PreparedStatement ps_select_one = connection.prepareStatement(SQL_SELECT_ONE);
            ps_select_one.setInt(1, id);
            //ResultSet mundo bdd a java
            ResultSet rs = ps_select_one.executeQuery();
            while (rs.next()) {
                odontologo = new Odontologo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
            }
            System.out.println("odontologo encontrado");

        } catch (Exception e) {
            e.getMessage();
        }
        return odontologo;
    }

    @Override
    public void eliminar(Integer id) {
        Connection connection = null;
        try {
            connection = BD.getConnection();
            Statement statement = connection.createStatement();
            PreparedStatement ps_delete_one = connection.prepareStatement(SQL_DELETE_ID);
            ps_delete_one.setInt(1, id);
            int odontologosEliminado = ps_delete_one.executeUpdate();

            if (odontologosEliminado > 0) {
                System.out.println("EL odontologo se elimino.");
            } else {
                System.out.println("No se encontro odontologo con id: " + id);
            }
        } catch (Exception e) {
            System.out.println("Error: "+ e.getMessage());
        }
    }

    @Override
    public void actualizar(Odontologo odontologo) {
        Connection connection = null;
        try {
            connection = BD.getConnection();
            Statement statement = connection.createStatement();
            PreparedStatement ps_update_one = connection.prepareStatement(SQL_UPDATE_ONE);
            ps_update_one.setString(1, odontologo.getNombre());
            ps_update_one.setString(2, odontologo.getApellido());
            ps_update_one.setString(3, odontologo.getMatricula());
            ps_update_one.setInt(4, odontologo.getId());
            int odontologoActualizado = ps_update_one.executeUpdate();

            if (odontologoActualizado > 0) {
                System.out.println("EL odontologo se actualizo.");
            } else {
                System.out.println("No se encontro odontologo con id: " + odontologo.getId());
            }
        } catch (Exception e) {
            System.out.println("Error: "+ e.getMessage());
        }
    }

    @Override
    public Odontologo buscarGenerico(String parametro) {
        return null;
    }

    @Override
    public List<Odontologo> buscarTodos() {
        Connection connection = null;
        Odontologo odontologo = null;
        List<Odontologo> odontologos = new ArrayList<>();;
        try {
            connection = BD.getConnection();
            Statement statement = connection.createStatement();
            PreparedStatement ps_select_all = connection.prepareStatement(SQL_SELECT_ALL);
            ResultSet rs = ps_select_all.executeQuery();
            while (rs.next()) {
                odontologo = new Odontologo(rs.getInt(1), rs.getString(2), rs.getString(3),  rs.getString(4));
                odontologos.add(odontologo);
            }
        } catch (Exception e) {
            System.out.println("Error: "+ e.getMessage());
        }
        return odontologos;
    }

    @Override
    public List<Odontologo> buscarPorString(String texto) {
        Connection connection = null;
        Odontologo odontologo = null;
        List<Odontologo> odontologos = new ArrayList<>();

        try {
            connection = BD.getConnection();
            Statement statement = connection.createStatement();
            PreparedStatement ps_select_string = connection.prepareStatement(SQL_SELECT_STRING);
            String campo = "%" + texto + "%";
            ps_select_string.setString(1, campo);
            ps_select_string.setString(2, campo);

            ResultSet rs = ps_select_string.executeQuery();
            DomicilioDAOH2 daoAux = new DomicilioDAOH2();

            while (rs.next()) {
                odontologo = new Odontologo(rs.getInt(1), rs.getString(2), rs.getString(3),  rs.getString(4));
                odontologos.add(odontologo);
            }

        } catch (Exception e) {
            System.out.println("Error buscando odontologo por texto: " + e.getMessage());
        }

        return odontologos;
    }

}