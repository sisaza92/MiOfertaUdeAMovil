package udea.edu.co.miofertaudea.modelo.dao.Implementations;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.Date;


import udea.edu.co.miofertaudea.modelo.dao.Contract;
import udea.edu.co.miofertaudea.modelo.dao.DbHelper;
import udea.edu.co.miofertaudea.modelo.dao.Interfaces.TandaDao;
import udea.edu.co.miofertaudea.modelo.dto.Tanda;

/**
 *
 * @author Santiago Ramirez
 * @see udea.edu.co.miofertaudea.modelo.dao.Interfaces.TandaDao
 */


public class TandaDaoImpl implements TandaDao {

    DbHelper dbHelper;
    SQLiteDatabase db;


    @Override
    public void saveTanda(Tanda tanda) {
        Log.d("REGISTRO -->"," CLASE: TandaDaoImpl METODO: saveTanda");
        dbHelper = new DbHelper();//Instancia de DbHelper
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.delete(Contract.TABLE_NAME_TANDA,null,null);

        values.put(Contract.Column.TANDA_NUMERO, tanda.getNumero());
        values.put(Contract.Column.TANDA_NOMBRE, tanda.getNombre());
        values.put(Contract.Column.TANDA_FECHA, tanda.getFecha());
        values.put(Contract.Column.TANDA_HORA_INICIAL, tanda.getHoraInicial());
        values.put(Contract.Column.TANDA_HORA_FINAL,tanda.getHoraFinal());

        Log.d("REGISTRO -->", tanda.toString());
        db.insertWithOnConflict(Contract.TABLE_NAME_TANDA, null, values, SQLiteDatabase.CONFLICT_IGNORE);


        db.close();

    }

    @Override
    public Tanda getTanda() {

        Log.d("REGISTRO -->"," CLASE: ProgramaDaoImpl METODO: getProgramas");
        dbHelper = new DbHelper();//Instancia de DbHelper
        db = dbHelper.getWritableDatabase();

        Tanda tanda = new Tanda();

        Cursor cursor = db.query(Contract.TABLE_NAME_TANDA,null,null,null,null,null,null);
        int nroRegistros = cursor.getCount();

        if( nroRegistros == 1){

            cursor.moveToNext();

            int indxNumero = cursor.getColumnIndex(Contract.Column.TANDA_NUMERO);
            int indxNombre = cursor.getColumnIndex(Contract.Column.TANDA_NOMBRE);
            int indxFecha = cursor.getColumnIndex(Contract.Column.TANDA_FECHA);
            int indxHoraInicial = cursor.getColumnIndex(Contract.Column.TANDA_HORA_INICIAL);
            int indxHoraFinal = cursor.getColumnIndex(Contract.Column.TANDA_HORA_FINAL);

            tanda.setNumero((long) cursor.getInt(indxNumero));
            tanda.setNombre(cursor.getString(indxNombre));
            tanda.setFecha(cursor.getString(indxFecha));
            tanda.setHoraInicial(cursor.getInt(indxHoraInicial));
            tanda.setHoraFinal(cursor.getInt(indxHoraFinal));
            Log.d("SE HA CONSULTADO: ", "Tanda: " + tanda.toString());

        }else{
            if (nroRegistros<1){
                Log.d("No retorna: ", "Tanda ------> Error");
            }
            Log.d("retorna: ", "Mas de una Tanda");
        }

        db.close();

        return tanda;
    }
}
