package udea.edu.co.miofertaudea.modelo.dao.Interfaces;

import java.util.List;

import udea.edu.co.miofertaudea.modelo.dto.Impedimento;

/**
 * Clase encargarda de brindar el acceso a la tabla Impedimento
 * almacenada en la base de datos del movil, permite almacerar y recuperar informacion
 * @author Santiago Ramirez
 */

public interface ImpedimentoDao {

    /**
     * metodo encargado de almacenar una lista de impedimento en la base de datos movil
     * @param impedimentos
     */
    public void saveImpedimentos(List<Impedimento> impedimentos);

    /**
     * metodo encargado para obtener un impedimento almacenada en la base de datos
     * @return Retorna una lista con todos los impedimentos almacenadas en la base de datos.
     */
    public List<Impedimento> getImpedimentos();

}
