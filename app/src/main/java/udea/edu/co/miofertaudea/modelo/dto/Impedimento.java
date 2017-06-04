package udea.edu.co.miofertaudea.modelo.dto;

/**
 * Representa un impedimento de matricula que será asociado a un estudiante.
 * @author CristianCamilo
 */
public class Impedimento {
    
    private Long semestre;
    private String tipo;
    private String nombre;

    public Impedimento(){

    }

    public Impedimento(Long semestre, String tipo, String nombre) {
        this.semestre = semestre;
        this.tipo = tipo;
        this.nombre = nombre;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Impedimento{" +
                "semestre=" + semestre +
                ", tipo='" + tipo + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
