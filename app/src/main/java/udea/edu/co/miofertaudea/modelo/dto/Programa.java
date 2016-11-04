package udea.edu.co.miofertaudea.modelo.dto;

/**
 * Representación Objetual de un Programa.
 * @author CristianCamilo
 */
public class Programa {
    
    private int codigoPrograma;
    private String nombrePrograma;
    private String estado;
    private String ultimoSemestre;

    public Programa(int codigoPrograma, String nombrePrograma, String estado, String ultimoSemestre) {
        this.codigoPrograma = codigoPrograma;
        this.nombrePrograma = nombrePrograma;
        this.estado = estado;
        this.ultimoSemestre = ultimoSemestre;
    }

    public Programa() {

    }


    
    
    public int getCodigoPrograma() {
        return codigoPrograma;
    }

    public void setCodigoPrograma(int codigoPrograma) {
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

    public String getUltimoSemestre() {
        return ultimoSemestre;
    }

    public void setUltimoSemestre(String ultimoSemestre) {
        this.ultimoSemestre = ultimoSemestre;
    }
    
    
}
