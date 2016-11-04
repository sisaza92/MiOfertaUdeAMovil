package udea.edu.co.miofertaudea.modelo.dao;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import udea.edu.co.miofertaudea.util.ContextProvider;

/**
 * Esta clase es la encargada de realizar operaciones de creacion  y actualizacion de las tablas
 * en la base de datos del dispositivo.
 * @author Created by Santiago Ramirez.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final String TAG = DbHelper.class.getSimpleName();

    /**
     * Método constructor de la clase.
     */
    public DbHelper(){
        super(ContextProvider.getContext(), Contract.DB_NAME, null, Contract.DB_VERSION);
        Log.d("REGISTRO -->"," CLASE: DbHelper METODO: constructor");
    }

    /**
     * Este método es ejecutado cuando se crea la base de datos y es el encargado de
     * crear las tablas necesarias para el buen funcionamiento de la aplicación.
     * @param db Base de datos en la cual se realizaran las operaciones.
     */
    @Override
    public void onCreate(SQLiteDatabase db){
        Log.d("REGISTRO -->"," CLASE: DbHelper METODO: onCreate");

        String sql1=String
                .format("create table %s(%s int,%s text, %s int, %s text, %s text)",
                        Contract.TABLE_NAME_MATERIA_OFERTADA,
                        Contract.Column.MATERIA_OFERTADA_CODIGO_MATERIA,
                        Contract.Column.MATERIA_OFERTADA_NOMBRE_MATERIA,
                        Contract.Column.MATERIA_OFERTADA_CREDITOS,
                        Contract.Column.MATERIA_OFERTADA_GRUPO,
                        Contract.Column.MATERIA_OFERTADA_HORARIO
                );

        String sql2=String
                .format("create table %s(%s int,%s text, %s text, %s text)",
                        Contract.TABLE_NAME_PROGRAMA,
                        Contract.Column.PROGRAMA_CODIGO_PROGRAMA,
                        Contract.Column.PROGRAMA_NOMBRE_PROGRAMA,
                        Contract.Column.PROGRAMA_ESTADO,
                        Contract.Column.PROGRAMA_ULTIMO_SEMESTRE
                );

        //Sentencia para crear tabla
        Log.d(TAG, "onCreate with SQL: " + sql1);
        db.execSQL(sql1);//Ejecución de la sentencia

        //Sentencia para crear tabla Programa
        Log.d(TAG, "onCreate with SQL: " + sql2);
        db.execSQL(sql2);//Ejecución de la sentencia sql


    }

    /**
     * Este método es llamado cada que el schema cambie para dar como resultado una nueva version.
     * @param db Base de datos con nuevo schema.
     * @param oldVersion Version antigua de la base de datos.
     * @param NewVersion Nueva version de la base de datos.
     */
    //Se llama cada que el schema cambie(nueva versión)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int NewVersion){
        Log.d("REGISTRO -->"," CLASE: DbHelper METODO: onUpgrade");
        db.execSQL("drop table if exists "+ Contract.TABLE_NAME_MATERIA_OFERTADA);//Borrar datos
        db.execSQL("drop table if exists "+ Contract.TABLE_NAME_PROGRAMA);//Borrar datos tabla programa
        onCreate(db);//Crear Tabla de nuevo

    }



}
