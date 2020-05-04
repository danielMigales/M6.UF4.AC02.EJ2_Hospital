package controlador;

import beans.Analitica;
import beans.Paciente;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import modelo.ConexionBD;

/**
 * FXML Controller class
 *
 * @author Daniel Migales Puertas
 *
 */
public class UIController implements Initializable {

    @FXML
    private MenuBar MenuBar;
    
    @FXML
    private MenuItem menuCerrar;

    @FXML
    private MenuItem menuExportarTXT;

    @FXML
    private Button botonBuscar;

    @FXML
    private TextField TextFieldNombreBuscador;

    @FXML
    private TextField TextFieldApellido1Buscador;

    @FXML
    private TextField TextFieldApellido2Buscador;

    @FXML
    private TextField TextfieldNumeroTelefonoPaciente;

    @FXML
    private TextField TextFieldNivelInsulinaActualBuscador;

    @FXML
    private TextField TextFieldNumeroSeguridadSocialBuscador;

    @FXML
    private Button botonAltaNueva;

    @FXML
    private TextField TextFieldNumeroSeguridadSocialAlta;

    @FXML
    private TextField TextFieldNombreAlta;

    @FXML
    private TextField TextFieldApellido1Alta;

    @FXML
    private TextField TextFieldApellido2Alta;

    @FXML
    private TextField TextFieldNivelInsulinaInicalAlta;

    @FXML
    private DatePicker DatePickerFechaNacimientoAlta;

    @FXML
    private TextField TextFieldFechaNacimientoBuscador;

    @FXML
    private Button botonMostrarPacientes;

    @FXML
    private Button botonGuardarAnalitica;

    @FXML
    private TextField TextFieldNumeroSSGuardarAnalitica;

    @FXML
    private TextField TextFieldResultadoAnaliticaGuardarAnalitica;

    @FXML
    private DatePicker DatePickerSelectorFechaGuardarAnalitica;

    @FXML
    private TextArea TextAreaConfirmaciones;

    @FXML
    private Button botonConsultarAnaliticas;

    @FXML
    private TextArea TextAreaHistorial;

    @FXML
    private TextField TextFieldNumeroSSConsultarAnaliticas;

    @FXML
    private Button botonLimpiarGuardarAnaliticas;

    @FXML
    private TextField TextfieldNumeroTelefonoAltaPaciente;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    void cerrarPrograma(ActionEvent event) {

        //cierra la aplicacion desde el menu cerrar
        Stage stage = (Stage) this.MenuBar.getScene().getWindow();
        stage.close();
    }

    @FXML
    void guardarArchivo(ActionEvent event) {

        //guarda los resultados en un archivo txt
        FileChooser fileChooser = new FileChooser();
        Stage stage = null;
        File file = fileChooser.showSaveDialog(stage);
        ConexionBD conexion = new ConexionBD();
        
        if (file != null) {
            FileWriter fw = null;
            BufferedWriter bw = null;
            try {
                fw = new FileWriter(file, false);
                bw = new BufferedWriter(fw);  
                String listadoPacientes = conexion.mostrarTodosPacientes().toString();
                bw.write(listadoPacientes);
            } catch (Exception ex) {
               
            } finally {
                try {
                    bw.close();
                } catch (Exception ex2) {
                   
                }
            }
        }
    }

    @FXML
    void BuscarPaciente(ActionEvent event) {//esta funcion busca un paciente en la bd a partir del numero de la seguridad social introducido 

        String numeroSS = TextFieldNumeroSeguridadSocialBuscador.getText();
        ConexionBD conexion = new ConexionBD();
        Paciente paciente = conexion.buscarPacienteNumero(numeroSS);

        //el resultado de la busqueda aparece en los campos de texto que hay debajo del buscador
        TextFieldNombreBuscador.setText(paciente.getNombre());
        TextFieldApellido1Buscador.setText(paciente.getApellido1());
        TextFieldApellido2Buscador.setText(paciente.getApellido2());
        TextfieldNumeroTelefonoPaciente.setText(paciente.getNumeroTelefono());
        TextFieldFechaNacimientoBuscador.setText(paciente.getFechaNacimiento());
        TextFieldNivelInsulinaActualBuscador.setText(String.valueOf(paciente.getNivelInsulina()));
    }

    @FXML
    void InsertarPaciente(ActionEvent event) {//este metodo obtiene los datos introducidos de los textfields y los inserta en la bd de pacientes

        String numeroSS = TextFieldNumeroSeguridadSocialAlta.getText();
        String nombre = TextFieldNombreAlta.getText();
        String apellido1 = TextFieldApellido1Alta.getText();
        String apellido2 = TextFieldApellido2Alta.getText();
        String numeroTelefono = TextfieldNumeroTelefonoAltaPaciente.getText();
        String fechaNacimiento = DatePickerFechaNacimientoAlta.getValue().toString();
        double nivelInsulina = Double.parseDouble(TextFieldNivelInsulinaInicalAlta.getText());

        ConexionBD conexion = new ConexionBD();
        conexion.darAltaPaciente(numeroSS, nombre, apellido1, apellido2, numeroTelefono, fechaNacimiento, nivelInsulina);

        //borra los campos de texto para indicar visualmente que ha habido una accion
        TextFieldNumeroSeguridadSocialAlta.clear();
        TextFieldNombreAlta.clear();
        TextFieldApellido1Alta.clear();
        TextFieldApellido2Alta.clear();
        TextfieldNumeroTelefonoAltaPaciente.clear();
        TextFieldNivelInsulinaInicalAlta.clear();
        DatePickerFechaNacimientoAlta.getEditor().clear();
    }

    @FXML
    void ConsultarPacientes(ActionEvent event) {//este metodo muestra en un cuadro de dialogo un listado con los pacientes registrados

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Base de datos de pacientes");
        alert.setHeaderText("Listado de pacientes");
        alert.setContentText("");

        Label label = new Label("Relacion de usuarios del Hospital:");
        ArrayList<Paciente> listadoPacientes = null;
        TextArea textArea = new TextArea();

        ConexionBD conexion = new ConexionBD();
        try {
            listadoPacientes = conexion.mostrarTodosPacientes();

            for (Paciente listadoPaciente : listadoPacientes) {

                String numeroSeguridadSocial = listadoPaciente.getNumeroSeguridadSocial();
                String nombre = listadoPaciente.getNombre();
                String apellido1 = listadoPaciente.getApellido1();
                String apellido2 = listadoPaciente.getApellido2();
                String numeroTelefono = listadoPaciente.getNumeroTelefono();
                String fechaNacimiento = listadoPaciente.getFechaNacimiento();
                String nivelInsulina = String.valueOf(listadoPaciente.getNivelInsulina());
                textArea.appendText("Numero de Seguridad Social: " + numeroSeguridadSocial + "\nNombre: " + nombre + "\n1er Apellido: "
                        + apellido1 + "\n2nd Apellido: " + apellido2 + "\nNumero de telefono: " + numeroTelefono
                        + "\nFecha de nacimiento: " + fechaNacimiento + "\nNivel de insulina: " + nivelInsulina + "\n\n");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UIController.class.getName()).log(Level.SEVERE, null, ex);
        }

        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);
        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);
        alert.getDialogPane().setExpandableContent(expContent);
        alert.showAndWait();
    }

    @FXML
    void GuardarAnaliticas(ActionEvent event) { //este metodo obtiene los datos introducidos de los textfields y los inserta en la bd de analiticas

        String numeroSS = TextFieldNumeroSSGuardarAnalitica.getText();
        String fechaAnalitica = DatePickerSelectorFechaGuardarAnalitica.getValue().toString();
        double nivelInsulina = Double.parseDouble(TextFieldResultadoAnaliticaGuardarAnalitica.getText());

        Analitica analitica = new Analitica();
        Paciente paciente;
        ConexionBD conexion = new ConexionBD();

        try {
            paciente = conexion.buscarPacienteNumero(numeroSS);//busco el usuario en la bd de pacientes y obtengo todos los campos
            analitica.setPaciente(paciente); //añado el paciente a la analitica
            paciente.addPropertyChangeListener(analitica);//Aviso de que esta accion puede generar un cambio

            analitica.setResultadoAnalitica(nivelInsulina); //asigno al objeto analitica el nuevo nivel y la fecha
            analitica.setFechaAnalitica(fechaAnalitica);

            double nuevo_nivel = analitica.getResultadoAnalitica(); //el nurvo nivel es el resultado de la analitica
            paciente.setNivelInsulina(nuevo_nivel); //cambiamos el nivel de insulina por el nuevo resultado

            String resultado = ("ANALISIS REALIZADO --> Su analitica se ha guardado");

            conexion.guardarAnalisisPaciente(analitica); //guardado en la tabla analiticas
            conexion.actualizarNivelInsulina(nivelInsulina);//actualizacion en la tabla pacientes

            TextFieldNumeroSSGuardarAnalitica.clear();
            DatePickerSelectorFechaGuardarAnalitica.getEditor().clear();
            TextFieldResultadoAnaliticaGuardarAnalitica.clear();
            TextAreaConfirmaciones.setText(resultado);

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @FXML
    void mostrarAnaliticasPaciente(ActionEvent event) { //esta funcion busca todas las analiticas que se ha hecho un paciente y las carga en el textarea

        String numeroSS = TextFieldNumeroSSConsultarAnaliticas.getText();
        ConexionBD conexion = new ConexionBD();
        ArrayList<Analitica> listadoAnaliticas = null;
        TextAreaHistorial.setWrapText(true);

        listadoAnaliticas = conexion.consultarAnalisisPaciente(numeroSS);

        for (Analitica listadoAnalitica : listadoAnaliticas) {

            int codigoAnalitica = listadoAnalitica.getCodigoAnalitica();
            String numeroSeguridadSocial = listadoAnalitica.getPaciente().getNumeroSeguridadSocial();
            String fechaAnalitica = listadoAnalitica.getFechaAnalitica();
            String resultadoAnalitica = String.valueOf(listadoAnalitica.getResultadoAnalitica());
            TextAreaHistorial.appendText("Numero de Analitica: " + codigoAnalitica + "\tNumero de Seguridad Social: " + numeroSeguridadSocial
                    + "\tFecha de analitica: " + fechaAnalitica + "\tNivel de insulina: " + resultadoAnalitica + "\n\n");
        }
    }

    @FXML
    void limpiarResultadosAnaliticas(ActionEvent event) {

        //este metodo simplemente borra los textos
        TextFieldNumeroSSGuardarAnalitica.clear();
        DatePickerSelectorFechaGuardarAnalitica.getEditor().clear();
        TextFieldResultadoAnaliticaGuardarAnalitica.clear();
        TextAreaConfirmaciones.clear();
    }

    @FXML
    void limpiarResultadosHistorialAnaliticas(ActionEvent event) { //limpia los cuadros de la busqueda de historial de analiticas

        TextFieldNumeroSSConsultarAnaliticas.clear();
        TextAreaHistorial.clear();

    }
}
