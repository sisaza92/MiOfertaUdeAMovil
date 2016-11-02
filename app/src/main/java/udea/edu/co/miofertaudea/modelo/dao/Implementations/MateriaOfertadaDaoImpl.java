package udea.edu.co.miofertaudea.modelo.dao.Implementations;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import udea.edu.co.miofertaudea.modelo.dao.Contract;
import udea.edu.co.miofertaudea.modelo.dao.DbHelper;
import udea.edu.co.miofertaudea.modelo.dao.Interfaces.MateriaOfertadaDao;
import udea.edu.co.miofertaudea.modelo.dto.MateriaOfertada;

/**
 * @see udea.edu.co.miofertaudea.modelo.dao.Interfaces.MateriaOfertadaDao
 */
public class MateriaOfertadaDaoImpl implements MateriaOfertadaDao {

    DbHelper dbHelper;
    SQLiteDatabase db;

    @Override
    public void saveAllMaterias(List<MateriaOfertada> materiaOfertadas) {

        Log.d("REGISTRO -->"," CLASE: MateriaOfertadaDaoImpl METODO: saveAllMaterias");
        dbHelper = new DbHelper();//Instancia de DbHelper
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.delete(Contract.TABLE_NAME_MATERIA_OFERTADA,null,null);

        int size = materiaOfertadas.size();
        MateriaOfertada materiaOfertada = null;


        for (int i=0;i<size;i++){
            materiaOfertada = materiaOfertadas.get(i);

            values.put(Contract.Column.MATERIA_OFERTADA_CODIGO_MATERIA, materiaOfertada.getCodigoMateria());
            values.put(Contract.Column.MATERIA_OFERTADA_NOMBRE_MATERIA, materiaOfertada.getNombreMateria());
            values.put(Contract.Column.MATERIA_OFERTADA_CREDITOS, materiaOfertada.getCreditos());
            values.put(Contract.Column.MATERIA_OFERTADA_GRUPO, materiaOfertada.getGrupo());
            values.put(Contract.Column.MATERIA_OFERTADA_HORARIO, materiaOfertada.getHorario());

            Log.d("REGISTRO -->", materiaOfertada.toString());
            db.insertWithOnConflict(Contract.TABLE_NAME_MATERIA_OFERTADA, null, values, SQLiteDatabase.CONFLICT_IGNORE);

        }
        db.close();
    }

    public List<MateriaOfertada> getAllMateriasOfertadas(){

        Log.d("REGISTRO -->"," CLASE: MateriaOfertadaDaoImpl METODO: getAllMateriasOfertadas");
        dbHelper = new DbHelper();//Instancia de DbHelper
        db = dbHelper.getWritableDatabase();

        List<MateriaOfertada> materiasOfertadas = new ArrayList<>();

        Cursor cursor = db.query(Contract.TABLE_NAME_MATERIA_OFERTADA,null,null,null,null,null,null);
        int nroRegistros = cursor.getCount();

        if( nroRegistros > 0){

            while (cursor.moveToNext()){
                MateriaOfertada materiaOfertada = new MateriaOfertada();

                int indxCodigoMateria = cursor.getColumnIndex(Contract.Column.MATERIA_OFERTADA_CODIGO_MATERIA);
                int indxNombreMateria = cursor.getColumnIndex(Contract.Column.MATERIA_OFERTADA_NOMBRE_MATERIA);
                int indxCreditos = cursor.getColumnIndex(Contract.Column.MATERIA_OFERTADA_CREDITOS);
                int indxGrupo = cursor.getColumnIndex(Contract.Column.MATERIA_OFERTADA_GRUPO);
                int indxHorario = cursor.getColumnIndex(Contract.Column.MATERIA_OFERTADA_HORARIO);

                materiaOfertada.setCodigoMateria(cursor.getString(indxCodigoMateria));
                materiaOfertada.setNombreMateria(cursor.getString(indxNombreMateria));
                materiaOfertada.setCreditos(cursor.getInt(indxCreditos));
                materiaOfertada.setGrupo(cursor.getString(indxGrupo));
                materiaOfertada.setHorario(cursor.getString(indxHorario));

                Log.d("CONSULTANDO", materiaOfertada.toString());
                materiasOfertadas.add(materiaOfertada);
            }
        }

        return materiasOfertadas;
    }
}
