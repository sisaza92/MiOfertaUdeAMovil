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


        //ojo
        values.put(Contract.Column.TANDA_NOMBRE, tanda.getNombre());
        values.put(Contract.Column.TANDA_NUMERO_TANDA, tanda.getNroTanda());
        values.put(Contract.Column.TANDA_FECHA, tanda.getFecha().getDate());
        values.put(Contract.Column.TANDA_HORA_INICIO, tanda.getHoraInicio().getHours());
        values.put(Contract.Column.TANDA_HORA_FIN,tanda.getHoraFin().getHours());

        Log.d("REGISTRO -->", tanda.toString());
        db.insertWithOnConflict(Contract.TABLE_NAME_PROGRAMA, null, values, SQLiteDatabase.CONFLICT_IGNORE);


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

            int indxNombre = cursor.getColumnIndex(Contract.Column.TANDA_NOMBRE);
            int indxNroTanda = cursor.getColumnIndex(Contract.Column.TANDA_NUMERO_TANDA);
            int indxFecha = cursor.getColumnIndex(Contract.Column.TANDA_FECHA);
            int indxHoraInicio = cursor.getColumnIndex(Contract.Column.TANDA_HORA_INICIO);
            int indxHoraFin = cursor.getColumnIndex(Contract.Column.TANDA_HORA_FIN);

            tanda.setNombre(cursor.getString(indxNombre));
            tanda.setNroTanda(cursor.getInt(indxNroTanda));
            Date fecha = new Date();
            fecha.setDate(cursor.getInt(indxFecha));
            tanda.setFecha(fecha);
            Date horaInicio = new Date();
            horaInicio.setDate(cursor.getInt(indxHoraInicio));
            tanda.setHoraInicio(horaInicio);
            Date horaFin = new Date();
            horaFin.setDate(cursor.getInt(indxHoraFin));
            tanda.setHoraFin(horaFin);
            Log.d("SE HA CONSULTADO: ", "Tanda: " + tanda.toString());

        }else{
            if (nroRegistros<1){
                Log.d("No retorna: ", "Tanda ------> Error");
            }
            Log.d("retorna: ", "Mas de una Tanda");
        }

        db.close();

        return null;
    }
}
