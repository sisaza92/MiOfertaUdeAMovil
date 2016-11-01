package udea.edu.co.miofertaudea.modelo.dao.Interfaces;

import java.util.List;

import udea.edu.co.miofertaudea.modelo.dto.Programa;

/**
 * Clase encargarda de brindar el acceso a la tabla Programa
 * almacenada en la base de datos del movil, permite almacerar y recuperar informacion
 * @author Santiago Ramirez
 */

public interface ProgramaDao {

    /**
     * metodo encargado de almacenar una lista de programas dados en la base de datos
     * @param programas
     */
    public void saveProgramas(List<Programa> programas);

    /**
     * metodo usado para obtener la lista de programas que se encuentran en la base de datos
     * @return
     */
    public List<Programa> getProgramas();
}
