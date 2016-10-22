package udea.edu.co.miofertaudea.modelo.dao.Interfaces;

import java.util.List;

import udea.edu.co.miofertaudea.modelo.dto.MateriaOfertada;

/**
 * Esta clase es la encargada de realizar las operaciones que requieren
 * acceso a la base de datos del dispositivo.
 * @author Created by CristianCamilo on 10/10/2016.
 */
public interface MateriaOfertadaDao {

    /**
     * Método Usado para guardar una lista de materias ofertadas en la base de datos del
     * dispositivo móvil.
     * @param materiaOfertadas
     */
    public void saveAllMaterias(List<MateriaOfertada> materiaOfertadas);

    /**
     * Método usado para retornar una lista de todas las materias ofertadas que han sido almacenadas
     * en la base de datos del dispositivo móvil.
     * @return Retorna una lista con todas las materias ofertadas almacenadas en la base de datos.
     */
    public List<MateriaOfertada> getAllMateriasOfertadas();
}
