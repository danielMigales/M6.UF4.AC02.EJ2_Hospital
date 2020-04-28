package beans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;

/**
 *
 * @author Daniel Migales Puertas
 * 
 */

//BEAN RECEPTOR
public class Analitica implements Serializable, PropertyChangeListener{
    
    private String numeroSeguridadSocial;
    private int resultadoAnalitica;
    private String fechaAnalitica;

    public Analitica() {
    }

    public Analitica(String numeroSeguridadSocial, int resultadoAnalitica, String fechaAnalitica) {
        this.numeroSeguridadSocial = numeroSeguridadSocial;
        this.resultadoAnalitica = resultadoAnalitica;
        this.fechaAnalitica = fechaAnalitica;
    }

    public String getNumeroSeguridadSocial() {
        return numeroSeguridadSocial;
    }

    public void setNumeroSeguridadSocial(String numeroSeguridadSocial) {
        this.numeroSeguridadSocial = numeroSeguridadSocial;
    }

    public int getResultadoAnalitica() {
        return resultadoAnalitica;
    }

    public void setResultadoAnalitica(int resultadoAnalitica) {
        this.resultadoAnalitica = resultadoAnalitica;
    }

    public String getFechaAnalitica() {
        return fechaAnalitica;
    }

    public void setFechaAnalitica(String fechaAnalitica) {
        this.fechaAnalitica = fechaAnalitica;
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        
        
        //AQUI VA EL CODIGO QUE HA DE HACER ALGO
        
        
        
    }
    
}
