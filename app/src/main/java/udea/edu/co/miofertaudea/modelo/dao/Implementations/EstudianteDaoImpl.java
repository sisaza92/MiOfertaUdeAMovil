package udea.edu.co.miofertaudea.modelo.dao.Implementations;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
        Log.d("REGISTRO -->"," CLASE: ProgramaDaoImpl METODO: saveProgramas");
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
        return null;
    }
}
