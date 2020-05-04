package beans;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

/**
 *
 * @author Daniel Migales Puertas
 *
 */
public class Paciente implements Serializable { //BEAN FUENTE

    private String numeroSeguridadSocial;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String numeroTelefono;
    String fechaNacimiento;
    private double nivelInsulina;//PROPIEDAD COMPARTIDA QUE Si esta propiedad cambia, susceptible de lanzar un evento

    private PropertyChangeSupport propertySupport;//BEAN FUENTE

    public Paciente() {
        propertySupport = new PropertyChangeSupport(this);
    }

    public Paciente(String numeroSeguridadSocial, String nombre, String apellido1, String apellido2,
            String numeroTelefono, String fechaNacimiento, double nivelInsulina) {

        propertySupport = new PropertyChangeSupport(this);//BEAN FUENTE
        this.numeroSeguridadSocial = numeroSeguridadSocial;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.numeroTelefono = numeroTelefono;
        this.fechaNacimiento = fechaNacimiento;
        this.nivelInsulina = nivelInsulina;
    }

    public String getNumeroSeguridadSocial() {
        return numeroSeguridadSocial;
    }

    public void setNumeroSeguridadSocial(String numeroSeguridadSocial) {
        this.numeroSeguridadSocial = numeroSeguridadSocial;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public double getNivelInsulina() {
        return nivelInsulina;
    }

    public void setNivelInsulina(double resultadoAnalitica) {

        double valorAnterior = this.nivelInsulina;
        this.nivelInsulina = resultadoAnalitica;

        //AQUI VA EL CODIGO QUE HA DE HACER ALGO
        double numeroA = valorAnterior;
        double numeroB = numeroA + 10;
        double numeroC = numeroA - 10;

        if (resultadoAnalitica >= numeroB) {
            //si la analitica es mayor que el nivel anterior se imprime mensaje y se lanza el evento
            System.out.println("SOY EL BEAN FUENTE: Sus niveles han sobrepasado los limites. Han subido mas de 10 puntos.");
            //LANZAMOS EL EVENTO AL RECEPTOR ENVIANDOLE LOS DATOS
            propertySupport.firePropertyChange("Nivel Insulina Actual", valorAnterior, this.nivelInsulina);
        }
        if (resultadoAnalitica <= numeroC) {
            //si la analitica es menor que el nivel anterior se imprime mensaje y se lanza el evento
            System.out.println("SOY EL BEAN FUENTE: Sus niveles han sobrepasado los limites. Han bajado mas de 10 puntos.");
            //LANZAMOS EL EVENTO AL RECEPTOR ENVIANDOLE LOS DATOS
            propertySupport.firePropertyChange("Nivel Insulina Actual", valorAnterior, this.nivelInsulina);
        }

    }

    @Override
    public String toString() {
        return "Paciente: " + "\nNumero Seguridad Social:" + numeroSeguridadSocial + "\nNombre: " + nombre
                + "\nApellido1: " + apellido1 + "\nApellido2: " + apellido2 + "\nNumero Telefono: " + numeroTelefono
                + "\nFecha Nacimiento: " + fechaNacimiento + "\nNivel Insulina=" + nivelInsulina + "\n";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }

}
