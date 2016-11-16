package udea.edu.co.miofertaudea.modelo.dao.Interfaces;

import java.util.List;

import udea.edu.co.miofertaudea.modelo.dto.Grupo;
import udea.edu.co.miofertaudea.modelo.dto.MateriaOfertada;

/**
 * Created by Jeniffer Acosta on 1/11/2016.
 */

public interface GrupoDao {

    /**
     * Método Usado para guardar los grupos que tiene una materia en la base de datos del
     * dispositivo móvil.
     * @param gruposPorMateria
     * @param idMateria
     */
    public void saveAllGrupos(List<Grupo> gruposPorMateria,String idMateria);

    /**
     * Método usado para retornar una lista de todos los grupos por materia que han sido almacenadas
     * en la base de datos del dispositivo móvil.
     * @return Retorna una lista con todos los grupos.
     */
    public List<Grupo> getAllGruposPorMateria ();

}
