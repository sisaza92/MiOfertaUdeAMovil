package udea.edu.co.miofertaudea.modelo.dao.Implementations;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import udea.edu.co.miofertaudea.modelo.dao.Contract;
import udea.edu.co.miofertaudea.modelo.dao.DbHelper;
import udea.edu.co.miofertaudea.modelo.dao.Interfaces.GrupoDao;
import udea.edu.co.miofertaudea.modelo.dto.Grupo;
/**
 * Created by Jeniffer Acosta
 */

public class GrupoDaoImpl implements GrupoDao{

    DbHelper dbHelper;
    SQLiteDatabase db;

    @Override
    public void saveAllGrupos(List<Grupo> grupos,String codigoMateria){

        Log.d("REGISTRO -->"," CLASE: GrupoDaoImpl METODO: saveAllGrupos");
        dbHelper = new DbHelper();//Instancia de DbHelper
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.delete(Contract.TABLE_NAME_GRUPO,null,null);

        int size = grupos.size();
        Grupo grupo = null;

        for (int i=0;i<size;i++){
            grupo = grupos.get(i);

            values.put(Contract.Column.GRUPO_ID_MATERIA,codigoMateria);
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
    public List<Grupo> getAllGruposPorMateria(String idMateria) {
        Log.d("REGISTRO -->"," CLASE: GrupoDaoImpl METODO: getAllGruposPorMateria");
        dbHelper = new DbHelper();//Instancia de DbHelper
        db = dbHelper.getWritableDatabase();
        String[] selectionArgs = {idMateria};

        List<Grupo> grupos = new ArrayList<>();

        Cursor cursor = db.query(Contract.TABLE_NAME_GRUPO,null, Contract.Column.GRUPO_ID_MATERIA +"=?",selectionArgs,null,null,null);
        int nroRegistros = cursor.getCount();

        if( nroRegistros > 0) {

            while (cursor.moveToNext()) {
                Grupo grupo = new Grupo();

                int indxGrupo = cursor.getColumnIndex(Contract.Column.GRUPO_ID);
                int indxCupoMaximo = cursor.getColumnIndex(Contract.Column.GRUPO_CUPO_MAXIMO);
                int indxCupoDisponible = cursor.getColumnIndex(Contract.Column.GRUPO_CUPO_DISPONIBLE);
                int indxAula = cursor.getColumnIndex(Contract.Column.GRUPO_AULA);
                int indxHorario = cursor.getColumnIndex(Contract.Column.GRUPO_HORARIO);
                int indxNombreProfesor = cursor.getColumnIndex(Contract.Column.GRUPO_NOMBRE_PROFESOR);

                grupo.setGrupo(cursor.getString(indxGrupo));
                grupo.setCupoMaximo(cursor.getInt(indxCupoMaximo));
                grupo.setCupoDisponible(cursor.getInt(indxCupoDisponible));
                grupo.setAula(cursor.getString(indxAula));
                grupo.setHorario(cursor.getString(indxHorario));
                grupo.setNombreProfesor(cursor.getString(indxNombreProfesor));

                Log.d("SE HA CONSULTADO: ", grupo.toString());
                grupos.add(grupo);
            }
        }
        db.close();
        return grupos;
    }

}
