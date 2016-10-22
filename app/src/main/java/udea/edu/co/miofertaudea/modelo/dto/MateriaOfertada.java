package udea.edu.co.miofertaudea.modelo.dto;

/**
 * Representa una materia que será ofertada a un estudiante.
 * @author CristianCamilo
 */
public class MateriaOfertada {
    
    private String codigoMateria;
    private String nombreMateria;
    private int creditos;
    //si se encuentra ya matriculada muestra el grupo y horario de lo contrario no muestra nada.????
    private String grupo;   // la materia ofertada aun no deberia tener grupo asignado.
    private String horario; // El horario deberia estár aqui? solo deberia pertenecer a grupo.

    public MateriaOfertada(String codigoMateria, String nombreMateria, int creditos) {
        this.codigoMateria = codigoMateria;
        this.nombreMateria = nombreMateria;
        this.creditos = creditos;
    }

    public MateriaOfertada() {

    }


    public String getCodigoMateria() {

        return codigoMateria;
    }

    public void setCodigoMateria(String codigomateria) {
        this.codigoMateria = codigomateria;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    @Override
    public String toString() {
        return "MateriaOfertada{" +
                "codigoMateria='" + codigoMateria + '\'' +
                ", nombreMateria='" + nombreMateria + '\'' +
                ", creditos=" + creditos +
                ", grupo='" + grupo + '\'' +
                ", horario='" + horario + '\'' +
                '}';
    }
}
