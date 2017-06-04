package udea.edu.co.miofertaudea.vista.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import udea.edu.co.miofertaudea.R;
import udea.edu.co.miofertaudea.modelo.dao.DbHelper;
import udea.edu.co.miofertaudea.modelo.dao.Implementations.EstudianteDaoImpl;
import udea.edu.co.miofertaudea.modelo.dao.Implementations.ProgramaDaoImpl;
import udea.edu.co.miofertaudea.modelo.dao.Interfaces.EstudianteDao;
import udea.edu.co.miofertaudea.modelo.dao.Interfaces.ProgramaDao;
import udea.edu.co.miofertaudea.modelo.dto.Estudiante;
import udea.edu.co.miofertaudea.modelo.dto.Programa;
import udea.edu.co.miofertaudea.service.ServiceImpl;
import udea.edu.co.miofertaudea.vista.adapter.ProgramaListAdapter;

import static udea.edu.co.miofertaudea.util.ContextProvider.getContext;

public class TempLoginActivity extends AppCompatActivity {

    private IntentFilter filtroEstudiante,filtroProgramas;
    private BroadcastReceiver receptorEstudiante, receptorProgramas;
    private EditText eTloginCedulaEstudiante, eTloginSemestreAcademico;
    private Long semestreAcademico;
    private Estudiante estudiante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        DbHelper dbHelper;
        SQLiteDatabase db;
        dbHelper = new DbHelper();//Instancia de DbHelper
        db = dbHelper.getWritableDatabase();
        dbHelper.deleteDB(db);

        //

        setContentView(R.layout.activity_temp_login);
        eTloginCedulaEstudiante = (EditText) findViewById(R.id.ETloginCedulaEstudiante);
        eTloginSemestreAcademico = (EditText) findViewById(R.id.ETloginSemestreAcademico);

        Log.d("REGISTRO -->", "CLASE: TempLoginActivity    METODO: onCreate");

        filtroEstudiante = new IntentFilter("udea.edu.co.miofertaudea.NUEVO_ESTUDIANTE");
        receptorEstudiante = new TimelineReceiverEstudiante();
        registerReceiver(receptorEstudiante, filtroEstudiante);

        filtroProgramas = new IntentFilter("udea.edu.co.miofertaudea.NUEVA_LISTA_PROGRAMAS");
        receptorProgramas = new TimelineReceiverProgramas();
        registerReceiver(receptorProgramas, filtroProgramas);
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    public void consultarOferta(View view) {
        String cedula = eTloginCedulaEstudiante.getText().toString();
        String semestreIngresado = eTloginSemestreAcademico.getText().toString();


        if(cedula==null || cedula.equals("")){
            Toast.makeText(TempLoginActivity.this, "Debe ingresar una cédula", Toast.LENGTH_LONG).show();
        }else{
            getEstudiante(cedula);
        }

        if(semestreIngresado==null || semestreIngresado.equals("")){
            Toast.makeText(TempLoginActivity.this, "Estudiante Recibido Exitosamente", Toast.LENGTH_LONG).show();
        }else{
            //verificar que si sean las que estan quemadas
            semestreAcademico = Long.parseLong(semestreIngresado);
        }
    }
    /**
     * Metodo que Crea un IntentService para llamar al servicio que obtiene la informacion del estudiante.
     */
    public void getEstudiante(String cedula) {
        Log.d("REGISTRO -->", "CLASE: TempLoginActivity    METODO: getEstudiante");
        Intent obtenerInfoEstudianteIntent = new Intent(TempLoginActivity.this, ServiceImpl.class);
        obtenerInfoEstudianteIntent.putExtra("accion", "obtenerInfoEstudiante");
        obtenerInfoEstudianteIntent.putExtra("cedulaEstudiante",cedula);
        startService(obtenerInfoEstudianteIntent);
    }

    /**
     * Metodo que Crea un IntentService para llamar al servicio que lista los programas.
     */
    private void getProgramas() {

        Log.d("REGISTRO -->", "CLASE: ProgramaActivity    METODO: getProgramas");
        Intent listarProgramasIntent = new Intent(TempLoginActivity.this, ServiceImpl.class);
        listarProgramasIntent.putExtra("cedulaEstudiante",estudiante.getCedula());
        listarProgramasIntent.putExtra("accion", "listarProgramas");
        startService(listarProgramasIntent);
    }


    class TimelineReceiverEstudiante extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("REGISTRO -->", "CLASE: TimelineReceiverEstudiante(TempLoginActivity)   METODO: onReceive");

            EstudianteDao estudianteDao = new EstudianteDaoImpl();
            Estudiante student = estudianteDao.getEstudiante();

            if(student != null){
                Log.d("IMPORTANTE-->", "CLASE: TimelineReceiverEstudiante(TempLoginActivity)" +
                        "   METODO: SI ESTA TRAYENDO ESTUDIANTE DE LA BD: " +student.toString());
                estudiante=student;
                getProgramas();
//                Intent intentestudiante = new Intent(context, ProgramaActivity.class);
//                intentestudiante.putExtra("ESTUDIANTE",(Serializable) estudiante);
//                context.startActivity(intentestudiante);

            }else{
                Log.d("CRITICO-->", "CLASE: TimelineReceiverEstudiante(TempLoginActivity)" +
                        "   METODO: NO ESTA TRAYENDO ESTUDIANTE DE LA BD");
            }
        }
    }

    class TimelineReceiverProgramas extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("REGISTRO -->", "CLASE: TimelineReceiverProgramas(TempLoginActivity)   METODO: onReceive");
            ProgramaDao programaDao = new ProgramaDaoImpl();
            List<Programa> programas = programaDao.getProgramas();

            if(programas.size()>0){
                Log.d("IMPORTANTE-->", "CLASE: TimelineReciver   METODO: SI ESTA TRAYENDO PROGRAMAS DE LA BD Y SON: ");

                for (Programa p: programas) {
                    Log.d("PROGRAMA -->:",p.toString());
                }

                if(programas.size()==1) {

                    Intent listarMaterias = new Intent(getContext(), Oferta_Ppal.class);
                    Programa program = programas.get(0);

                    Log.d("REGISTRO -->","CLASE: ProgramaListAdapter, METODO: getListener se tiene el estudiante: "+
                            estudiante.toString());


                    Log.d("REGISTRO -->", "CLASE: TempLoginActivity    METODO: consultarOferta ---->" +
                            "SemestreIngresado: " +semestreAcademico.toString());

                    // se agrega la informacion a enviar a la actividad
                    listarMaterias.putExtra("ESTUDIANTE",estudiante);
                    listarMaterias.putExtra("PROGRAMA", program);
                    listarMaterias.putExtra("semestreAcademico",semestreAcademico);

                    // se inicia la otra actividad
                    startActivity(listarMaterias);


                }else {

                    Log.d("BROADCAST RECIBIDO", "onReceived");
                    ProgramaListAdapter programaListAdapter = new ProgramaListAdapter((Activity) context, (ArrayList<Programa>) programas,
                            estudiante, semestreAcademico);
                    AlertDialog.Builder builderSingle = new AlertDialog.Builder(TempLoginActivity.this);

                    builderSingle.setTitle("Seleccione un Programa");
                    builderSingle.setAdapter(programaListAdapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builderSingle.show();
                }


            }else{
                Log.d("CRITICO-->", "CLASE: TimelineReciver   METODO: NO ESTA TRAYENDO PROGRAMAS DE LA BD");
                Toast.makeText(TempLoginActivity.this, "Con la información ", Toast.LENGTH_LONG).show();
            }

        }
    }

}
