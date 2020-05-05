package modelo;

/**
 *
 * @author Daniel Migales Puertas
 *
 */
public class Visitas {

    private int numeroVisita;
    private String numeroSeguridadSocial;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String fechaVisita;
    private String hora;

    public Visitas() {
    }

    public Visitas(int numeroVisita, String numeroSeguridadSocial, String nombre, String apellido1, String apellido2, String fechaVisita, String hora) {
        this.numeroVisita = numeroVisita;
        this.numeroSeguridadSocial = numeroSeguridadSocial;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.fechaVisita = fechaVisita;
        this.hora = hora;
    }

    public int getNumeroVisita() {
        return numeroVisita;
    }

    public void setNumeroVisita(int numeroVisita) {
        this.numeroVisita = numeroVisita;
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

    public String getFechaVisita() {
        return fechaVisita;
    }

    public void setFechaVisita(String fechaVisita) {
        this.fechaVisita = fechaVisita;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "Visitas: " + "\nNumeroVisita: " + numeroVisita + "\nNumero Seguridad Social: " + numeroSeguridadSocial
                + "\nNombre: " + nombre + "\nApellido 1: " + apellido1 + "\nApellido 2: " + apellido2
                + "\nFecha Visita: " + fechaVisita + "\nHora=" + hora;
    }

}
