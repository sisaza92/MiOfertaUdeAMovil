package udea.edu.co.miofertaudea.modelo.dao.Interfaces;

import udea.edu.co.miofertaudea.modelo.dto.Tanda;

/**
 * Clase encargarda de brindar el acceso a la tabla Tanda
 * almacenada en la base de datos del movil, permite almacerar y recuperar informacion
 * @author Santiago Ramirez
 */

public interface TandaDao {

    /**
     * metodo encargado de almacenar una tanda dada como parametro en la base de datos
     * @param tanda
     */
    public void saveTanda(Tanda tanda);

    /**
     * metodo encargado para obtener una tanda almacenada en la base de datos
     */
    public Tanda getTanda();

}
