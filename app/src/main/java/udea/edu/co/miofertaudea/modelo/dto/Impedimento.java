package udea.edu.co.miofertaudea.modelo.dto;

/**
 * Representa un impedimento de matricula que será asociado a un estudiante.
 * @author CristianCamilo
 */
public class Impedimento {
    
    private Long semestre;
    private String tipo;
    private String impedimento;

    public Impedimento(){

    }

    public Impedimento(Long semestre, String tipo, String impedimento) {
        this.semestre = semestre;
        this.tipo = tipo;
        this.impedimento = impedimento;
    }

    public Long getSemestre() {
        return semestre;
    }

    public void setSemestre(Long semestre) {
        this.semestre = semestre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getImpedimento() {
        return impedimento;
    }

    public void setImpedimento(String impedimento) {
        this.impedimento = impedimento;
    }

    @Override
    public String toString() {
        return "Impedimento{" +
                "semestre=" + semestre +
                ", tipo='" + tipo + '\'' +
                ", impedimento='" + impedimento + '\'' +
                '}';
    }
}
