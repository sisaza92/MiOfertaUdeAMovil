package udea.edu.co.miofertaudea.vista.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import udea.edu.co.miofertaudea.R;
import udea.edu.co.miofertaudea.modelo.dao.Implementations.ProgramaDaoImpl;
import udea.edu.co.miofertaudea.modelo.dao.Interfaces.ProgramaDao;
import udea.edu.co.miofertaudea.modelo.dto.Programa;
import udea.edu.co.miofertaudea.service.ServiceImpl;

/**
 * Created by Santiago on 01/11/2016.
 */
public class ProgramaActivity extends AppCompatActivity {

    private IntentFilter filtro;
    private BroadcastReceiver receptor;
    private Spinner spinerProgramas;
    private ArrayList<String> lista = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("REGISTRO -->", "CLASE: ProgramaActivity    METODO: onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.prueba_programa_layout);

        // spinerProgramas se utiliza para setear el spiner del layout
        spinerProgramas = (Spinner) findViewById(R.id.spinner_programa);
        filtro = new IntentFilter("udea.edu.co.miofertaudea.NUEVA_LISTA");
        spinerProgramas.setOnItemClickListener((AdapterView.OnItemClickListener) this);


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
        listarProgramas.putExtra("accion", "listarMaterias");
        startService(listarProgramas);
        ProgramaDao programaDao = new ProgramaDaoImpl();
        List<Programa> programas = programaDao.getProgramas();
    }


    class TimelineReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d("REGISTRO -->", "CLASE: TimelineReciver   METODO: onReceive");
            ProgramaDao programaDao = new ProgramaDaoImpl();
            List<Programa> programas = programaDao.getProgramas();
            Log.d("BROADCAST RECIBIDO", "onReceived");
            ArrayList<String> programass;
            //spinerProgramas.setAdapter();        //R
            //listaMaterias.setAdapter(new MateriaOfertadaListAdapter((Activity) context, (ArrayList<MateriaOfertada>) materiasOfertadas));
        }
    }

}
