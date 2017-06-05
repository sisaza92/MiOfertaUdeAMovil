package udea.edu.co.miofertaudea.modelo.dao.Implementations;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import udea.edu.co.miofertaudea.modelo.dao.Contract;
import udea.edu.co.miofertaudea.modelo.dao.DbHelper;
import udea.edu.co.miofertaudea.modelo.dao.Interfaces.ImpedimentoDao;
import udea.edu.co.miofertaudea.modelo.dto.Impedimento;
import udea.edu.co.miofertaudea.modelo.dto.MateriaOfertada;

/**
 * @see ImpedimentoDao
 */
public class ImpedimentoDaoImpl implements ImpedimentoDao  {

    DbHelper dbHelper;
    SQLiteDatabase db;



    @Override
    public void saveImpedimentos(List<Impedimento> impedimentos) {
        Log.d("REGISTRO -->"," CLASE: ImpedimentoDaoImpl METODO: saveImpedimentos");
        dbHelper = new DbHelper();//Instancia de DbHelper
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.delete(Contract.TABLE_NAME_IMPEDIMENTO,null,null);

        int size = impedimentos.size();
        Impedimento impedimento = null;

        for (int i=0;i<size;i++){
            impedimento = impedimentos.get(i);

            values.put(Contract.Column.IMPEDIMENTO_SEMESTRE, impedimento.getSemestre());
            values.put(Contract.Column.IMPEDIMENTO_TIPO, impedimento.getTipo());
            values.put(Contract.Column.IMPEDIMENTO_NOMBRE, impedimento.getNombre());


            Log.d("REGISTRO -->", impedimento.toString());
            db.insertWithOnConflict(Contract.TABLE_NAME_IMPEDIMENTO, null, values, SQLiteDatabase.CONFLICT_IGNORE);

        }
        db.close();

    }

    @Override
    public List<Impedimento> getImpedimentos() {
        Log.d("REGISTRO -->"," CLASE: ImpedimentoDaoImpl METODO: saveImpedimentos");
        dbHelper = new DbHelper();//Instancia de DbHelper
        db = dbHelper.getWritableDatabase();

        List<Impedimento> impedimentos = new ArrayList<>();

        Cursor cursor = db.query(Contract.TABLE_NAME_IMPEDIMENTO,null,null,null,null,null,null);
        int nroRegistros = cursor.getCount();

        if( nroRegistros > 0){

            while (cursor.moveToNext()){
                Impedimento impedimento = new Impedimento();

                int indxSemestre = cursor.getColumnIndex(Contract.Column.IMPEDIMENTO_SEMESTRE);
                int indxTipo = cursor.getColumnIndex(Contract.Column.IMPEDIMENTO_TIPO);
                int indxNombre = cursor.getColumnIndex(Contract.Column.IMPEDIMENTO_NOMBRE);


                impedimento.setSemestre(cursor.getLong(indxSemestre));
                impedimento.setTipo(cursor.getString(indxTipo));
                impedimento.setNombre(cursor.getString(indxNombre));


                Log.d("CONSULTANDO", impedimento.toString());
                impedimentos.add(impedimento);
            }
        }
        db.close();
        return impedimentos;
    }
}
