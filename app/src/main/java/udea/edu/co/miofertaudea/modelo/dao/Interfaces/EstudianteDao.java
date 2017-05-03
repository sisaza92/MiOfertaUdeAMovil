package udea.edu.co.miofertaudea.modelo.dao.Interfaces;

import udea.edu.co.miofertaudea.modelo.dto.Estudiante;

/**
 * Created by Santiago on 29/11/2016.
 */
public interface EstudianteDao {

    /**
     *
     * @param estudiante
     */
    public void saveEstudiante(Estudiante estudiante);


    /**
     *
     * @return
     */
    public Estudiante getEstudiante();
}
