package udea.edu.co.miofertaudea.modelo.dto;

import java.io.Serializable;

/**
 * Representación Objetual de un Programa.
 * @author Santiago Ramirez
 */
public class Programa implements Serializable {
    
    private long codigoPrograma;
    private String nombrePrograma;
    private String estado;
    //private String ultimoSemestre;


    public Programa(long codigoPrograma, String nombrePrograma, String estado) {
        this.codigoPrograma = codigoPrograma;
        this.nombrePrograma = nombrePrograma;
        this.estado = estado;
    }

    public Programa() {

    }


    
    
    public long getCodigoPrograma() {
        return codigoPrograma;
    }

    public void setCodigoPrograma(long codigoPrograma) {
        this.codigoPrograma = codigoPrograma;
    }

    public String getNombrePrograma() {
        return nombrePrograma;
    }

    public void setNombrePrograma(String nombrePrograma) {
        this.nombrePrograma = nombrePrograma;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return  "  "+codigoPrograma + "  " + nombrePrograma + "\n  Estado: " +
                estado;
    }
}
