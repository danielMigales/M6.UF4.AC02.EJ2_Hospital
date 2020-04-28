package controlador;

import beans.Paciente;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import modelo.ConexionBD;

/**
 * FXML Controller class
 *
 * @author Daniel Migales Puertas
 *
 */
public class UIController implements Initializable {

    @FXML
    private Button botonBuscar;

    @FXML
    private TextField TextFieldNombreBuscador;

    @FXML
    private TextField TextFieldApellido1Buscador;

    @FXML
    private TextField TextFieldApellido2Buscador;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    void BuscarPaciente(ActionEvent event) {

        //esta funcion busca un paciente en la bd a partir del numero de la seguridad social introducido 
        //y llena con los datos obtenidos los textfields
        String numeroSS = TextFieldNumeroSeguridadSocialBuscador.getText();
        ConexionBD conexion = new ConexionBD();
        Paciente paciente = conexion.BuscarPaciente(numeroSS);

        TextFieldNombreBuscador.setText(paciente.getNombre());
        TextFieldApellido1Buscador.setText(paciente.getApellido1());
        TextFieldApellido2Buscador.setText(paciente.getApellido2());
        TextFieldFechaNacimientoBuscador.setText(paciente.getFechaNacimiento());
        TextFieldNivelInsulinaActualBuscador.setText(String.valueOf(paciente.getNivelInsulina()));

    }

    @FXML
    void InsertarPaciente(ActionEvent event) {

        //este metodo obtiene los datos introducidos de los textfields y los inserta en la bd de pacientes
        String numeroSS = TextFieldNumeroSeguridadSocialAlta.getText();
        String nombre = TextFieldNombreAlta.getText();
        String apellido1 = TextFieldApellido1Alta.getText();
        String apellido2 = TextFieldApellido2Alta.getText();
        String fechaNacimiento = DatePickerFechaNacimientoAlta.getValue().toString();
        double nivelInsulina = Double.parseDouble(TextFieldNivelInsulinaInicalAlta.getText());

        ConexionBD conexion = new ConexionBD();
        conexion.insertarPaciente(numeroSS, nombre, apellido1, apellido2, fechaNacimiento, nivelInsulina);
    }

    @FXML
    void ConsultarPacientes(ActionEvent event) {

    }

    @FXML
    void GuardarAnaliticas(ActionEvent event) {

    }

}
