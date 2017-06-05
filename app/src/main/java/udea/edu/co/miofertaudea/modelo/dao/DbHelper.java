package udea.edu.co.miofertaudea.modelo.dao;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import udea.edu.co.miofertaudea.util.ContextProvider;

/**
 * Esta clase es la encargada de realizar operaciones de creacion  y actualizacion de las tablas
 * en la base de datos del dispositivo.
 * @author Santiago
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

        String sqlMateriaOfertada=String
                .format("create table %s(%s int,%s text, %s int, %s text, %s text)",
                        Contract.TABLE_NAME_MATERIA_OFERTADA,
                        Contract.Column.MATERIA_OFERTADA_CODIGO_MATERIA,
                        Contract.Column.MATERIA_OFERTADA_NOMBRE_MATERIA,
                        Contract.Column.MATERIA_OFERTADA_CREDITOS,
                        Contract.Column.MATERIA_OFERTADA_GRUPO,
                        Contract.Column.MATERIA_OFERTADA_HORARIO
                );

        String sqlPrograma=String
                .format("create table %s(%s long,%s text, %s text)",
                        Contract.TABLE_NAME_PROGRAMA,
                        Contract.Column.PROGRAMA_CODIGO_PROGRAMA,
                        Contract.Column.PROGRAMA_NOMBRE_PROGRAMA,
                        Contract.Column.PROGRAMA_ESTADO
                );

        String sqlGrupo=String
                .format("create table %s(%s text,%s text,%s int, %s int, %s text,%s text,%s text)",
                        Contract.TABLE_NAME_GRUPO,
                        Contract.Column.GRUPO_ID_MATERIA,
                        Contract.Column.GRUPO_ID,
                        Contract.Column.GRUPO_CUPO_DISPONIBLE,
                        Contract.Column.GRUPO_CUPO_MAXIMO,
                        Contract.Column.GRUPO_AULA,
                        Contract.Column.GRUPO_HORARIO,
                        Contract.Column.GRUPO_NOMBRE_PROFESOR
                );

        String sqlTanda=String
                .format("create table %s(%s int,%s text,%s text, %s int, %s int)",
                        Contract.TABLE_NAME_TANDA,
                        Contract.Column.TANDA_NUMERO,
                        Contract.Column.TANDA_NOMBRE,
                        Contract.Column.TANDA_FECHA,
                        Contract.Column.TANDA_HORA_INICIAL,
                        Contract.Column.TANDA_HORA_FINAL
                );


        String sqlEstudiante=String
                .format("create table %s(%s text,%s text,%s text,%s text,%s text)",
                        Contract.TABLE_NAME_ESTUDIANTE,
                        Contract.Column.ESTUDIANTE_CEDULA_ESTUDIANTE,
                        Contract.Column.ESTUDIANTE_NOMBRES,
                        Contract.Column.ESTUDIANTE_APELLIDOS,
                        Contract.Column.ESTUDIANTE_FECHA_NACIMIENTO ,
                        Contract.Column.ESTUDIANTE_EMAL
                );

        String sqlImpedimento=String
                .format("create table %s(%s long,%s text, %s text)",
                        Contract.TABLE_NAME_IMPEDIMENTO,
                        Contract.Column.IMPEDIMENTO_SEMESTRE,
                        Contract.Column.IMPEDIMENTO_TIPO,
                        Contract.Column.IMPEDIMENTO_NOMBRE
                );



        //Sentencia para crear tabla
        Log.d(TAG, "onCreate with SQL: " + sqlMateriaOfertada);
        db.execSQL(sqlMateriaOfertada);//Ejecución de la sentencia sqlMateriaOfertada

        //Sentencia para crear tabla Programa
        Log.d(TAG, "onCreate with SQL: " + sqlPrograma);
        db.execSQL(sqlPrograma);//Ejecución de la sentencia sqlPrograma

        //Sentencia para crear tabla grupo
        Log.d(TAG, "onCreate with SQL: " + sqlGrupo);
        db.execSQL(sqlGrupo);//Ejecución de la sentencia sqlGrupo

        //Sentencia para crear tabla grupo
        Log.d(TAG, "onCreate with SQL: " + sqlTanda);
        db.execSQL(sqlTanda);//Ejecución de la sentencia sqlTanda

        //Sentencia para crear tabla Estudiante
        db.execSQL(sqlEstudiante);//Ejecución de la sentencia sqlEstudiante

        //Sentencia para crear tabla Impedimento
        db.execSQL(sqlImpedimento);//Ejecución de la sentencia sqlImpedimento

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
        db.execSQL("drop table if exists "+ Contract.TABLE_NAME_GRUPO);//Borrar datos tabla grupo
        db.execSQL("drop table if exists "+ Contract.TABLE_NAME_TANDA);//Borrar datos tabla tanda
        db.execSQL("drop table if exists "+ Contract.TABLE_NAME_ESTUDIANTE);//Borrar datos tabla estudiante
        db.execSQL("drop table if exists "+ Contract.TABLE_NAME_IMPEDIMENTO);//Borrar datos tabla Impedimento
        onCreate(db);//Crear Tabla de nuevo

    }

    public void deleteDB(SQLiteDatabase db){
        Log.d("REGISTRO -->"," CLASE: DbHelper METODO: onUpgrade");
        db.execSQL("drop table if exists "+ Contract.TABLE_NAME_MATERIA_OFERTADA);//Borrar datos
        db.execSQL("drop table if exists "+ Contract.TABLE_NAME_PROGRAMA);//Borrar datos tabla programa
        db.execSQL("drop table if exists "+ Contract.TABLE_NAME_GRUPO);//Borrar datos tabla grupo
        db.execSQL("drop table if exists "+ Contract.TABLE_NAME_TANDA);//Borrar datos tabla tanda
        db.execSQL("drop table if exists "+ Contract.TABLE_NAME_ESTUDIANTE);//Borrar datos tabla estudiante
        db.execSQL("drop table if exists "+ Contract.TABLE_NAME_IMPEDIMENTO);//Borrar datos tabla estudiante
        onCreate(db);//Crear Toda la base de datos nuevamente
    }



}
