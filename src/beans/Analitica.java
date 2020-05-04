package beans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author Daniel Migales Puertas
 *
 */
//BEAN RECEPTOR
public class Analitica implements Serializable, PropertyChangeListener {

    private int codigoAnalitica;
    private Paciente paciente;
    private String fechaAnalitica;
    private double resultadoAnalitica;

    public Analitica() {
    }

    public Analitica(int codigoAnalitica, Paciente paciente, String fechaAnalitica, double resultadoAnalitica) {
        this.codigoAnalitica = codigoAnalitica;
        this.paciente = paciente;
        this.fechaAnalitica = fechaAnalitica;
        this.resultadoAnalitica = resultadoAnalitica;
    }

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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {//AQUI VA EL CODIGO QUE HA DE HACER ALGO

        //variables para saber cuanto ha cambiado e imprimir
        double valorAntiguo = (double) evt.getOldValue();
        double valorNuevo = (double) evt.getNewValue();
        double diferencia = valorAntiguo - valorNuevo;

        //si se recibe un evento se lanza mensaje para la terminal
        System.out.println("MENSAJE DEL BEAN RECEPTOR: Nivel anterior: " + evt.getOldValue()
                + "Nivel actual: " + evt.getNewValue());
        System.out.printf("BEAN RECEPTOR ACTUO. VOLVEMOS A REPETIR LA ANALITICA EN DOS DIAS.");

        //Lanzara una alerta en forma de dialogo
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("ALERTA DE ANALITICA");
        alert.setHeaderText("SOY EL BEAN RECEPTOR E INFORMO:");
        alert.setContentText("El nivel anterior del paciente era de " + valorAntiguo + ".\n" + "El resultado de la analitica de hoy es de "
                + valorNuevo + ". \nSus valores han variado en " + diferencia + "\nSe debe programar una nueva analitica en una semana." 
                        + "\nEfectue llamada al numero " + paciente.getNumeroTelefono()+ " para programar visita con el paciente." 
                + paciente.getNombre() + "" + paciente.getApellido1() + "\n\n");
        alert.showAndWait();

        //AQUI HARIA ALGO PARA PROGRAMAR UNA ANALITICA NUEVA (PODRIA SER UNA TABLA VISITAS CON UNA ANALITICA PROGRAMADA)
        
    }

    @Override
    public String toString() {
        return "Analitica: " + "\nCodigo Analitica: " + codigoAnalitica + "\nPaciente: " + paciente
                + "\nFechaAnalitica: " + fechaAnalitica + "\nResultado Analitica: " + resultadoAnalitica;
    }

}
