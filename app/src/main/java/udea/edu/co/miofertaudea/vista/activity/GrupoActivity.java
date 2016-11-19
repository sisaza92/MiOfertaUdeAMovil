package udea.edu.co.miofertaudea.vista.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import udea.edu.co.miofertaudea.R;
import udea.edu.co.miofertaudea.modelo.dao.Implementations.GrupoDaoImpl;
import udea.edu.co.miofertaudea.modelo.dao.Implementations.ProgramaDaoImpl;
import udea.edu.co.miofertaudea.modelo.dao.Interfaces.GrupoDao;
import udea.edu.co.miofertaudea.modelo.dao.Interfaces.ProgramaDao;
import udea.edu.co.miofertaudea.modelo.dto.Grupo;
import udea.edu.co.miofertaudea.modelo.dto.Programa;
import udea.edu.co.miofertaudea.service.ServiceImpl;
import udea.edu.co.miofertaudea.vista.adapter.GrupoListAdapter;
import udea.edu.co.miofertaudea.vista.adapter.ProgramaListAdapter;

/**
 * Created by Santiago Ramirez on 16/11/2016.
 */
public class GrupoActivity extends AppCompatActivity {

    private IntentFilter filtro;
    private BroadcastReceiver receptor;

    ListView listaGrupos;
    TextView cabecera;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("REGISTRO -->", "CLASE: GrupoActivity    METODO: onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo_layout);
        listaGrupos = (ListView) findViewById(R.id.listViewGrupo);
        cabecera = (TextView)findViewById(R.id.textViewCabeceraGrupo);
        filtro = new IntentFilter("udea.edu.co.miofertaudea.NUEVA_LISTA_GRUPOS");

        String codigoMateria =  getIntent().getStringExtra("codigoMateria");
        String nombreMateria = getIntent().getStringExtra("nombreMateria");

        //Log.d("IMPORTANTE -->", "CLASE: GrupoActivity   METODO: onCreate" +
        //        " el codigo de la materia que resibe es: "+idMateria);
        cabecera.setText(nombreMateria + " ("+codigoMateria+")" );
        getGrupos();
    }

    @Override
    public void onResume() {
        super.onResume();
        receptor = new TimelineReceiver();
        registerReceiver(receptor, filtro);
    }


    /**
     * Metodo que Crea un IntentService para llamar al servicio que lista los grupos de una materia.
     */
    private void getGrupos() {

        Log.d("REGISTRO -->", "CLASE: GrupoActivity   METODO: getGrupos");
        String codigoMateria =  getIntent().getStringExtra("codigoMateria");
        Log.d("IMPORTANTE -->", "CLASE: GrupoActivity   METODO: getGrupos" +
                " el codigo de la materia que recibe es: "+codigoMateria);
        Intent listarProgramas = new Intent(GrupoActivity.this, ServiceImpl.class);
        listarProgramas.putExtra("accion", "listarGrupos");
        listarProgramas.putExtra("codigoMateria",codigoMateria);
        startService(listarProgramas);

    }


    class TimelineReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d("REGISTRO -->", "CLASE: TimelineReciver(GrupoActivity)   METODO: onReceive");
            String codigoMateria =  getIntent().getStringExtra("codigoMateria");

            GrupoDao grupoDao = new GrupoDaoImpl();
            List<Grupo> grupos = grupoDao.getAllGruposPorMateria(codigoMateria);
            if(grupos.size()>0){
                Log.d("IMPORTANTE-->", "CLASE: TimelineReciver   METODO: SI ESTA TRAYENDO PROGRAMAS DE LA BD Y SON: ");
                for (Grupo g: grupos) {
                    Log.d("PROGRAMA -->:",g.toString());
                }
            }else{
                Log.d("CRITICO-->", "CLASE: TimelineReciver   METODO: NO ESTA TRAYENDO PROGRAMAS DE LA BD");
            }
            Log.d("BROADCAST RECIBIDO", "onReceived");

            listaGrupos.setAdapter(new GrupoListAdapter((Activity) context, (ArrayList<Grupo>) grupos));

        }
    }

}
