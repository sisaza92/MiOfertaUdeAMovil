package udea.edu.co.miofertaudea.vista.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import udea.edu.co.miofertaudea.R;
import udea.edu.co.miofertaudea.modelo.dao.Implementations.ProgramaDaoImpl;
import udea.edu.co.miofertaudea.modelo.dao.Interfaces.ProgramaDao;
import udea.edu.co.miofertaudea.modelo.dto.Programa;
import udea.edu.co.miofertaudea.service.ServiceImpl;
import udea.edu.co.miofertaudea.vista.adapter.ProgramaListAdapter;

/**
 * Created by Santiago on 01/11/2016.
 */
public class ProgramaActivity extends AppCompatActivity {

    private IntentFilter filtro;
    private BroadcastReceiver receptor;

    ListView listaProgramas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("REGISTRO -->", "CLASE: ProgramaActivity    METODO: onCreate");
        super.onCreate(savedInstanceState);
        listaProgramas = (ListView) findViewById(R.id.listViewPrograma);
        filtro = new IntentFilter("udea.edu.co.miofertaudea.NUEVA_LISTA");
        getProgramas();
    }

    @Override
    public void onResume() {
        super.onResume();
        receptor = new TimelineReceiver();
        registerReceiver(receptor, filtro);
    }


    /**
     * Metodo que Crea un IntentService para llamar al servicio que lista los programas.
     */
    private void getProgramas() {

        Log.d("REGISTRO -->", "CLASE: ProgramaActivity    METODO: getProgramas");
        Intent listarProgramas = new Intent(ProgramaActivity.this, ServiceImpl.class);
        listarProgramas.putExtra("accion", "listarProgramas");
        startService(listarProgramas);
    }


    class TimelineReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d("REGISTRO -->", "CLASE: TimelineReciver   METODO: onReceive");
            ProgramaDao programaDao = new ProgramaDaoImpl();
            List<Programa> programas = programaDao.getProgramas();
            if(programas.size()>0){
                Log.d("IMPORTANTE-->", "CLASE: TimelineReciver   METODO: SI ESTA TRAYENDO PROGRAMAS DE LA BD Y SON: ");
                for (Programa p: programas) {
                    Log.d("PROGRAMA -->:",p.toString());
                }
            }else{
                Log.d("CRITICO-->", "CLASE: TimelineReciver   METODO: NO ESTA TRAYENDO PROGRAMAS DE LA BD");
                }
            Log.d("BROADCAST RECIBIDO", "onReceived");

            listaProgramas.setAdapter(new ProgramaListAdapter((Activity) context, (ArrayList<Programa>) programas));

        }
    }

}
