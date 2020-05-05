package beans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import modelo.ConexionBD;

/**
 *
 * @author Daniel Migales Puertas
 *
 */
public class Analitica implements Serializable, PropertyChangeListener {//BEAN RECEPTOR

    private int codigoAnalitica;
    private Paciente paciente;
    private String fechaAnalitica;
    private double resultadoAnalitica;

    //constructor vacio
    public Analitica() {
    }

    //constructor normal con todos los parametros
    public Analitica(int codigoAnalitica, Paciente paciente, String fechaAnalitica, double resultadoAnalitica) {
        this.codigoAnalitica = codigoAnalitica;
        this.paciente = paciente;
        this.fechaAnalitica = fechaAnalitica;
        this.resultadoAnalitica = resultadoAnalitica;
    }

    //getters y setters
    public int getCodigoAnalitica() {
        return codigoAnalitica;
    }

    public void setCodigoAnalitica(int codigoAnalitica) {
        this.codigoAnalitica = codigoAnalitica;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public String getFechaAnalitica() {
        return fechaAnalitica;
    }

    public void setFechaAnalitica(String fechaAnalitica) {
        this.fechaAnalitica = fechaAnalitica;
    }

    public double getResultadoAnalitica() {
        return resultadoAnalitica;
    }

    public void setResultadoAnalitica(double resultadoAnalitica) {
        this.resultadoAnalitica = resultadoAnalitica;
    }

    //metodo que actua cuando se produce un cambio en el resultado de la analitica
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        //variables para saber cuanto ha cambiado
        double valorAntiguo = (double) evt.getOldValue();
        double valorNuevo = (double) evt.getNewValue();
        double diferencia = (valorAntiguo - valorNuevo) * (-1);

        //si se recibe un evento se lanza otro mensaje para confirmar que se ha recibido. Se imprime en la terminal y salta un dialogo
        System.out.println("MENSAJE DEL BEAN RECEPTOR: Nivel anterior: " + evt.getOldValue()
                + "Nivel actual: " + evt.getNewValue());
        System.out.printf("BEAN RECEPTOR ACTUO. VOLVEMOS A REPETIR LA ANALITICA EN DOS DIAS.");

        //Lanzara una alerta en forma de dialogo
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("ALERTA DE ANALITICA");
        alert.setHeaderText("SOY EL BEAN RECEPTOR E INFORMO:");
        alert.setContentText("El nivel anterior del paciente era de " + valorAntiguo + ".\n" + "El resultado de la analitica de hoy es de "
                + valorNuevo + ". \nSus valores han variado en " + diferencia
                + "\nENVIO AUTOMATICO DE SMS al numero " + paciente.getNumeroTelefono() + " de"
                + paciente.getNombre() + " " + paciente.getApellido1() + " programando nueva analitica mañana a las 9h\n\n");
        alert.showAndWait();

        //AQUI PROGRAMO UNA ANALITICA NUEVA. SE CREA UN REGISTRO EN LA TABLA DE VISITAS CON FECHA DEL DIA SIGUIENTE A LAS 9 DE LA MAÑANA  
        String hora = "09:00";
        //con estas lineas obtengo la fecha de hoy y le sumo 1 (puede ser cualquier cantidad de dias), finalmente la formateo para guardarla
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        String fechaMañana = formatter.format(cal.getTime());
        //realiza la conexion y inserta en la tabla todos los datos actuales
        ConexionBD conexion = new ConexionBD();
        conexion.programarVisitaPaciente(paciente.getNumeroSeguridadSocial(), paciente.getNombre(), paciente.getApellido1(),
                paciente.getApellido2(), fechaMañana, hora);
    }

    @Override
    public String toString() {
        return "Analitica: " + "\nCodigo Analitica: " + codigoAnalitica + "\nPaciente: " + paciente
                + "\nFechaAnalitica: " + fechaAnalitica + "\nResultado Analitica: " + resultadoAnalitica;
    }

}
