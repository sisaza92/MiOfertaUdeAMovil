package udea.edu.co.miofertaudea.modelo.dao.Implementations;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import udea.edu.co.miofertaudea.modelo.dao.Contract;
import udea.edu.co.miofertaudea.modelo.dao.DbHelper;
import udea.edu.co.miofertaudea.modelo.dao.Interfaces.ProgramaDao;
import udea.edu.co.miofertaudea.modelo.dto.Programa;

/**
 *
 * @author Santiago Ramirez
 * @see udea.edu.co.miofertaudea.modelo.dao.Interfaces.ProgramaDao
 */

public class ProgramaDaoImpl implements ProgramaDao {

    DbHelper dbHelper;
    SQLiteDatabase db;


    @Override
    public void saveProgramas(List<Programa> programas) {

        Log.d("REGISTRO -->"," CLASE: ProgramaDaoImpl METODO: saveProgramas");
        dbHelper = new DbHelper();//Instancia de DbHelper
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.delete(Contract.TABLE_NAME_PROGRAMA,null,null);

        int size = programas.size();
        Programa programa = null;


        for (int i=0;i<size;i++){
            programa = programas.get(i);

            values.put(Contract.Column.PROGRAMA_CODIGO_PROGRAMA, programa.getCodigoPrograma());
            values.put(Contract.Column.PROGRAMA_NOMBRE_PROGRAMA, programa.getNombrePrograma());
            values.put(Contract.Column.PROGRAMA_ESTADO, programa.getEstado());
            values.put(Contract.Column.PROGRAMA_ULTIMO_SEMESTRE, programa.getUltimoSemestre());

            Log.d("REGISTRO -->", programa.toString());
            db.insertWithOnConflict(Contract.TABLE_NAME_PROGRAMA, null, values, SQLiteDatabase.CONFLICT_IGNORE);

        }
        db.close();

    }

    @Override
    public List<Programa> getProgramas() {

        Log.d("REGISTRO -->"," CLASE: ProgramaDaoImpl METODO: getProgramas");
        dbHelper = new DbHelper();//Instancia de DbHelper
        db = dbHelper.getWritableDatabase();

        List<Programa> programas = new ArrayList<>();

        Cursor cursor = db.query(Contract.TABLE_NAME_PROGRAMA,null,null,null,null,null,null);
        int nroRegistros = cursor.getCount();

        if( nroRegistros > 0){

            while (cursor.moveToNext()){
                Programa programa = new Programa();

                int indxCodigoPrograma = cursor.getColumnIndex(Contract.Column.PROGRAMA_CODIGO_PROGRAMA);
                int indxNombreMateria = cursor.getColumnIndex(Contract.Column.PROGRAMA_NOMBRE_PROGRAMA);
                int indxEstado = cursor.getColumnIndex(Contract.Column.PROGRAMA_ESTADO);
                int indxUltimoSemestre = cursor.getColumnIndex(Contract.Column.PROGRAMA_ULTIMO_SEMESTRE);

                programa.setCodigoPrograma(cursor.getInt(indxCodigoPrograma));
                programa.setNombrePrograma(cursor.getString(indxNombreMateria));
                programa.setEstado(cursor.getString(indxEstado));
                programa.setUltimoSemestre(cursor.getString(indxUltimoSemestre));

                Log.d("SE HA CONSULTADO: ", programa.toString());
                programas.add(programa);
            }
        }
        db.close();
        return programas;
    }
}
