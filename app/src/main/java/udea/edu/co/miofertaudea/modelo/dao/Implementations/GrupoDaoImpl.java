package udea.edu.co.miofertaudea.modelo.dao.Implementations;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.List;

import udea.edu.co.miofertaudea.modelo.dao.Contract;
import udea.edu.co.miofertaudea.modelo.dao.DbHelper;
import udea.edu.co.miofertaudea.modelo.dao.Interfaces.GrupoDao;
import udea.edu.co.miofertaudea.modelo.dto.Grupo;
/**
 * Created by Jeniffer Acosta on 1/11/2016.
 */

public class GrupoDaoImpl implements GrupoDao{

    DbHelper dbHelper;
    SQLiteDatabase db;

    @Override
    public void saveAllGrupos(List<Grupo> gruposPorMateria){

        Log.d("REGISTRO -->"," CLASE: GrupoDaoImpl METODO: saveAllGrupos");
        dbHelper = new DbHelper();//Instancia de DbHelper
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.delete(Contract.TABLE_NAME_GRUPO,null,null);

        int size = gruposPorMateria.size();
        Grupo grupo = null;

        for (int i=0;i<size;i++){
            grupo = gruposPorMateria.get(i);

            values.put(Contract.Column.GRUPO_ID, grupo.getGrupo());
            values.put(Contract.Column.GRUPO_CUPO_MAXIMO , grupo.getCupoMaximo());
            values.put(Contract.Column.GRUPO_CUPO_DISPONIBLE, grupo.getCupoDisponible());
            values.put(Contract.Column.GRUPO_AULA, grupo.getAula());
            values.put(Contract.Column.GRUPO_HORARIO, grupo.getHorario());
            values.put(Contract.Column.GRUPO_NOMBRE_PROFESOR , grupo.getNombreProfesor());


            Log.d("REGISTRO -->", grupo.toString());
            db.insertWithOnConflict(Contract.TABLE_NAME_GRUPO, null, values, SQLiteDatabase.CONFLICT_IGNORE);

        }
        db.close();

    }

    @Override
    public List<Grupo> getAllGruposPorMateria() {
        return null;
    }

}
