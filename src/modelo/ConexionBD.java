package modelo;

import beans.Paciente;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniel Migales Puertas
 *
 */
public class ConexionBD {

    //datos de la base de datos 
    private static Connection conection;
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String BD = "hospital";
    private static final String TABLE1 = "pacientes";
    private static final String TABLE2 = "analiticas";

    //constructor de la conexion
    public ConexionBD() {

        conection = null;
        try {
            Class.forName(DRIVER);
            conection = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ConexionBD.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    //funcion de desconectar de la bd
    public void desconectar() {
        conection = null;
    }

    //procedimiento que busca y muestra un paciente a traves de su id (numero seguridad social)
    public Paciente BuscarPaciente(String numeroSS) {

        Statement st = null;
        String sql = "SELECT * FROM " + TABLE1 + " WHERE numeroSeguridadSocial = " + "'" + numeroSS + "';";
        System.out.println(sql);

        Paciente paciente = null;

        try {
            conection = (Connection) DriverManager.getConnection(URL + BD, USER, PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            st = conection.createStatement();
            try (ResultSet rs = st.executeQuery(sql)) {
                int resultados = 0;

                while (rs.next()) {
                    String numeroSeguridadSocial = rs.getString("numeroSeguridadSocial");
                    String nombre = rs.getString("nombre");
                    String apellido1 = rs.getString("apellido1");
                    String apellido2 = rs.getString("apellido2");
                    String fechaNacimiento = rs.getString("fechaNacimiento");
                    double nivelInsulina = rs.getDouble("nivelInsulina");
                    paciente = new Paciente(numeroSeguridadSocial, nombre, apellido1, apellido2, fechaNacimiento, nivelInsulina);
                    System.out.println(paciente);
                    resultados++;
                }
                if (resultados == 0) {
                    System.out.println("No se ha encontrado ningun resultado.");
                }
            } catch (SQLException ex) {
                Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        System.out.println(paciente.toString());
        return paciente;
    }

    //procedimiento para insertar un paciente nuevo en la bd de pacientes
    public void insertarPaciente(String numeroSeguridadSocial, String nombre, String apellido1, String apellido2,
            String fechaNacimiento, double nivelInsulina) {

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conection = (Connection) DriverManager.getConnection(URL + BD, USER, PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        PreparedStatement ps = null;

        try {
            ps = (PreparedStatement) conection.prepareStatement("CREATE TABLE IF NOT EXISTS " + TABLE1
                    + "(numeroSeguridadSocial VARCHAR(20) PRIMARY KEY, nombre VARCHAR (20), apellido1 VARCHAR (20), "
                    + "apellido2 VARCHAR (20), fechaNacimiento VARCHAR (20), nivelInsulina DOUBLE )");
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Tabla " + TABLE1 + " creada o actualizada.");

        String sql = "INSERT INTO " + TABLE1 + "(numeroSeguridadSocial, nombre, apellido1, apellido2, fechaNacimiento, nivelInsulina ) "
                + "values ('" + numeroSeguridadSocial + "', '" + nombre + "', '" + apellido1 + "', '" + apellido2
                + "', '" + fechaNacimiento + "', '" + nivelInsulina + "')";
        System.out.println(sql);

        try (Statement st = conection.createStatement()) {
            st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            System.out.println("Datos añadidos a la tabla.");
            try (ResultSet rs = st.getGeneratedKeys()) {
                rs.next();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
}
