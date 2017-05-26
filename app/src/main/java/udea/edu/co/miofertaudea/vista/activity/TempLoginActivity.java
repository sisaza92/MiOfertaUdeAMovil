package udea.edu.co.miofertaudea.vista.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;

import udea.edu.co.miofertaudea.R;
import udea.edu.co.miofertaudea.modelo.dao.Implementations.EstudianteDaoImpl;
import udea.edu.co.miofertaudea.modelo.dao.Implementations.ProgramaDaoImpl;
import udea.edu.co.miofertaudea.modelo.dao.Interfaces.EstudianteDao;
import udea.edu.co.miofertaudea.modelo.dto.Estudiante;
import udea.edu.co.miofertaudea.service.ServiceImpl;

public class TempLoginActivity extends AppCompatActivity {

    private IntentFilter filtro;
    private BroadcastReceiver receptor;
    private EditText ETloginCedulaEstudiante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_login);
        ETloginCedulaEstudiante = (EditText) findViewById(R.id.ETloginCedulaEstudiante);
        Log.d("REGISTRO -->", "CLASE: TempLoginActivity    METODO: onCreate");
        filtro = new IntentFilter("udea.edu.co.miofertaudea.NUEVO_ESTUDIANTE");

        receptor = new TimelineReceiver();
        registerReceiver(receptor, filtro);
    }

    @Override
    public void onResume() {
        super.onResume();
        /*
        lo que estaba aqui era lo que causaba que se abrieran varias actividades debido a que
        creaba "receptor = new TimelineReceiver();" nuevamente
         */

    }

    public void consultarOferta(View view) {
        String cedula = ETloginCedulaEstudiante.getText().toString();
        if(cedula==null){
            Toast.makeText(TempLoginActivity.this, "Estudiante Recibido Exitosamente", Toast.LENGTH_LONG).show();
        }else{
            //verificar que si sean las que estan quemadas
            getEstudiante(cedula);

        }



    }
    /**
     * Metodo que Crea un IntentService para llamar al servicio que obtiene la informacion del estudiante.
     */
    public void getEstudiante(String cedula) {
        Log.d("REGISTRO -->", "CLASE: TempLoginActivity    METODO: getEstudiante");
        Intent obtenerEstudiante = new Intent(TempLoginActivity.this, ServiceImpl.class);
        obtenerEstudiante.putExtra("accion", "obtenerEstudiante");
        obtenerEstudiante.putExtra("cedulaEstudiante",cedula);
        startService(obtenerEstudiante);
    }


    class TimelineReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("BROADCAST RECIBIDO", "onReceived");
            Log.d("REGISTRO -->", "CLASE: TimelineReciver(TempLoginActivity)   METODO: onReceive");
            EstudianteDao estudianteDao = new EstudianteDaoImpl();
            Estudiante estudiante = estudianteDao.getEstudiante();
            if(estudiante != null){
                Log.d("IMPORTANTE-->", "CLASE: TimelineReciver(TempLoginActivity)" +
                        "   METODO:onReceive  SI ESTA TRAYENDO ESTUDIANTE DE LA BD: " +estudiante.toString());
                Intent intentEstudiante = new Intent(context, ProgramaActivity.class);
                intentEstudiante.putExtra("ESTUDIANTE",estudiante);
                context.startActivity(intentEstudiante);

            }else{
                Log.d("CRITICO-->", "CLASE: TimelineReciver(TempLoginActivity)" +
                        "   METODO:onReceive NO ESTA TRAYENDO ESTUDIANTE DE LA BD");
            }

        }
    }

}
