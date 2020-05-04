package modelo;

import beans.Analitica;
import beans.Paciente;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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

    //metodo que busca y muestra un paciente a traves de su id (numero seguridad social)
    public Paciente buscarPacienteNumero(String numeroSS) {

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
                    String numeroTelefono = rs.getString("numeroTelefono");
                    String fechaNacimiento = rs.getString("fechaNacimiento");
                    double nivelInsulina = rs.getDouble("nivelInsulina");
                    paciente = new Paciente(numeroSeguridadSocial, nombre, apellido1, apellido2, numeroTelefono, fechaNacimiento, nivelInsulina);
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
    public void darAltaPaciente(String numeroSeguridadSocial, String nombre, String apellido1, String apellido2,
            String numeroTelefono, String fechaNacimiento, double nivelInsulina) {

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
                    + "apellido2 VARCHAR (20), numeroTelefono VARCHAR(20), fechaNacimiento VARCHAR (20), nivelInsulina DOUBLE )");
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Tabla " + TABLE1 + " creada o actualizada.");

        String sql = "INSERT INTO " + TABLE1 + "(numeroSeguridadSocial, nombre, apellido1, apellido2, numeroTelefono, fechaNacimiento, "
                + "nivelInsulina ) " + "values ('" + numeroSeguridadSocial + "', '" + nombre + "', '" + apellido1 + "', '" + apellido2
                + "', '" + numeroTelefono + "', '" + fechaNacimiento + "', '" + nivelInsulina + "')";
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

    //metodo que muestra todos los pacientes de la bd en un cuadro de dialogo
    public ArrayList<Paciente> mostrarTodosPacientes() throws SQLException {

        Paciente paciente = null;
        ArrayList<Paciente> listadoPacientes = new ArrayList<>();

        Statement st = null;
        String sql = "SELECT * FROM " + TABLE1 + ";";
        conection = (Connection) DriverManager.getConnection(URL + BD, USER, PASSWORD);
        try {
            st = conection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            int resultados = 0;

            while (rs.next()) {
                String numeroSeguridadSocial = rs.getString("numeroSeguridadSocial");
                String nombre = rs.getString("nombre");
                String apellido1 = rs.getString("apellido1");
                String apellido2 = rs.getString("apellido2");
                String numeroTelefono = rs.getString("numeroTelefono");
                String fechaNacimiento = rs.getString("fechaNacimiento");
                double nivelInsulina = rs.getDouble("nivelInsulina");
                paciente = new Paciente(numeroSeguridadSocial, nombre, apellido1, apellido2, numeroTelefono, fechaNacimiento, nivelInsulina);
                listadoPacientes.add(paciente);
                resultados++;
            }
            if (resultados == 0) {
                System.out.println("No se ha encontrado ningun resultado.");
            }
            rs.close();
        } finally {
            if (st != null) {
                st.close();
            }
        }
        System.out.println(listadoPacientes);
        return listadoPacientes;
    }

    //procedimiento que guarda las analiticas realizadas en la base de datos
    public void guardarAnalisisPaciente(Analitica analitica) {

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
            ps = (PreparedStatement) conection.prepareStatement("CREATE TABLE IF NOT EXISTS " + TABLE2
                    + "(codigoAnalitica int(11) AUTO_INCREMENT PRIMARY KEY, numeroSeguridadSocial VARCHAR(20), "
                    + "fechaAnalitica VARCHAR (20), resultadoAnalitica DOUBLE)");
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Tabla " + TABLE2 + " creada o actualizada.");

        String sql = "INSERT INTO " + TABLE2 + "(numeroSeguridadSocial, fechaAnalitica, resultadoAnalitica ) "
                + "values ('" + analitica.getPaciente().getNumeroSeguridadSocial()
                + "', '" + analitica.getFechaAnalitica() + "', '" + analitica.getResultadoAnalitica() + "')";
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

    //metodo para consultar todos los datos de la tabla analiticas segun un numero de Seguridad social
    public ArrayList<Analitica> consultarAnalisisPaciente(String numeroSS) {

        Statement st = null;
        String sql = "SELECT * FROM " + TABLE2 + " WHERE numeroSeguridadSocial = " + "'" + numeroSS + "';";
        System.out.println(sql);

        Analitica analitica;
        Paciente paciente = new Paciente();
        ArrayList<Analitica> listadoAnaliticas = new ArrayList();

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
                    int codigoAnalitica = rs.getInt("codigoAnalitica");
                    String numeroSeguridadSocial = rs.getString("numeroSeguridadSocial");
                    paciente.setNumeroSeguridadSocial(numeroSeguridadSocial);
                    String fechaAnalitica = rs.getString("fechaAnalitica");
                    double resultadoAnalitica = rs.getDouble("resultadoAnalitica");
                    analitica = new Analitica(codigoAnalitica, paciente, fechaAnalitica, resultadoAnalitica);
                    listadoAnaliticas.add(analitica);
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
        return listadoAnaliticas;
    }

    //metodo para cambiar el nivel de insulina de la tabla pacientes cuando se haga una nueva analitica
    public void actualizarNivelInsulina(double nuevoNivelInsulina) {

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

        String sql = "UPDATE " + TABLE1 + " SET nivelInsulina = " + nuevoNivelInsulina;
        System.out.println(sql);

        try (Statement st = conection.createStatement()) {
            st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            System.out.println("Datos actualizados en la tabla.");
            try (ResultSet rs = st.getGeneratedKeys()) {
                rs.next();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
