package udea.edu.co.miofertaudea.modelo.dto;

import android.view.View;

/**
 * Representa uno de varios posibles grupos asociados a una misma materia, estos
 * grupos pueden tener distintos horarios, aulas y profesores y los valores de 
 * sus cupos también puede variar.
 * @author CristianCamilo
 */
public class Grupo {
    
    private String grupo;
    private int cupoMaximo;
    private int cupoDisponible;
    private String aula;
    private String horario;
    private String nombreProfesor;

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    public void setCupoMaximo(int cupoMaximo) {
        this.cupoMaximo = cupoMaximo;
    }

    public int getCupoDisponible() {
        return cupoDisponible;
    }

    public void setCupoDisponible(int cupoDisponible) {
        this.cupoDisponible = cupoDisponible;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getNombreProfesor() {
        return nombreProfesor;
    }

    public void setNombreProfesor(String nombreProfesor) {
        this.nombreProfesor = nombreProfesor;
    }

    public String toStringGrupoHorario(){
        return  "Grupo: "+grupo+"  Horario: "+horario;
    }

    public String toStringCupos(){
        return  "Cupos disponibles: " + cupoDisponible+"  Cupo maximo: "+cupoMaximo;
    }

}
