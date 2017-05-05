package udea.edu.co.miofertaudea.modelo.dao.Implementations;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Date;

import udea.edu.co.miofertaudea.modelo.dao.Contract;
import udea.edu.co.miofertaudea.modelo.dao.DbHelper;
import udea.edu.co.miofertaudea.modelo.dao.Interfaces.EstudianteDao;
import udea.edu.co.miofertaudea.modelo.dto.Estudiante;

/**
 * Created by Santiago on 29/11/2016.
 */
public class EstudianteDaoImpl implements EstudianteDao{
    DbHelper dbHelper;
    SQLiteDatabase db;

    /**
     * @param estudiante
     */
    @Override
    public void saveEstudiante(Estudiante estudiante) {
        Log.d("REGISTRO -->"," CLASE: EstudianteDaoImpl METODO: saveEstudiante");
        dbHelper = new DbHelper();//Instancia de DbHelper
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.delete(Contract.TABLE_NAME_ESTUDIANTE,null,null);
    }

    /**
     * @return
     */
    @Override
    public Estudiante getEstudiante() {
        Log.d("REGISTRO -->"," CLASE: EstudianteDaoImpl METODO: getEstudiante");
        dbHelper = new DbHelper();//Instancia de DbHelper
        db = dbHelper.getWritableDatabase();

        Estudiante estudiante = new Estudiante();

        //hacer query para encontarlo por la cedula
        Cursor cursor = db.query(Contract.TABLE_NAME_ESTUDIANTE,null,null,null,null,null,null);
        int nroRegistros = cursor.getCount();

        if( nroRegistros == 1){

            cursor.moveToNext();

            int indxNombres = cursor.getColumnIndex(Contract.Column.ESTUDIANTE_NOMBRES);
            int indxApellidos = cursor.getColumnIndex(Contract.Column.ESTUDIANTE_APELLIDOS);
            int indxCedula = cursor.getColumnIndex(Contract.Column.ESTUDIANTE_CEDULA_ESTUDIANTE);
            int indxFechaDeNacimiento = cursor.getColumnIndex(Contract.Column.ESTUDIANTE_FECHA_NACIMIENTO);
            int indxEmail = cursor.getColumnIndex(Contract.Column.ESTUDIANTE_EMAL);


            estudiante.setNombres(cursor.getString(indxNombres));
            estudiante.setApellidos(cursor.getString(indxApellidos));
            estudiante.setCedula(cursor.getString(indxCedula));
            estudiante.setFechaDeNacimiento(cursor.getString(indxFechaDeNacimiento));
            estudiante.setEmail(cursor.getString(indxEmail));
            Log.d("SE HA CONSULTADO: ", "Estudiante: " + estudiante.toString());

        }else{
            if (nroRegistros<1){
                Log.d("La BD no retorna: ", "Estudiante ------> Error");
            }
            Log.d("La BD retorna: ", "Mas de un Estudiante ------> Error");
        }

        db.close();

        return estudiante;
    }
}
