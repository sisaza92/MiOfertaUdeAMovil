package udea.edu.co.miofertaudea.modelo.dto;

/**
 * Representa un impedimento de matricula que será asociado a un estudiante.
 * @author CristianCamilo
 */
public class Impedimento {
    
    private String semestre;
    private String impedimento;

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getImpedimento() {
        return impedimento;
    }

    public void setImpedimento(String impedimento) {
        this.impedimento = impedimento;
    }
    
}
