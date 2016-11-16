package udea.edu.co.miofertaudea.vista.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import udea.edu.co.miofertaudea.R;
import udea.edu.co.miofertaudea.modelo.dao.Implementations.MateriaOfertadaDaoImpl;
import udea.edu.co.miofertaudea.modelo.dao.Interfaces.MateriaOfertadaDao;
import udea.edu.co.miofertaudea.modelo.dto.MateriaOfertada;
import udea.edu.co.miofertaudea.service.ServiceImpl;
import udea.edu.co.miofertaudea.vista.adapter.MateriaOfertadaListAdapter;

public class Oferta_Ppal extends AppCompatActivity {

    private IntentFilter filtro;
    private BroadcastReceiver receptor;

    Fragment materiasFragment;
    ListView listaMaterias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oferta_ppal);
        /*
        //Paso 1: Obtener la instancia del administrador de fragmentos
        FragmentManager fragmentManager = getFragmentManager();

        //Paso 2: Crear una nueva transacción
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        //Paso 3: Crear un nuevo fragmento y añadirlo

        InfoAcademicaFragment infoAcademicaFragment = new InfoAcademicaFragment();
        MateriasListFragment materiasListFragment = new MateriasListFragment();
        ImpedimentosFragment impedimentosFragment = new ImpedimentosFragment();




        transaction.add(R.id.mainLayout, infoAcademicaFragment);
        transaction.add(R.id.mainLayout, materiasListFragment);
        transaction.add(R.id.mainLayout, impedimentosFragment);



        //Paso 4: Confirmar el cambio
        transaction.commit();*/


        Log.d("REGISTRO -->","CLASE: Oferta_Ppal      METODO: onCreate");
        listaMaterias = (ListView)findViewById(R.id.lista_materias);
        filtro = new IntentFilter("udea.edu.co.miofertaudea.NUEVA_LISTA");
        getAllMateriasOfertadas();

    }

    @Override
    public void onResume() {
        super.onResume();
        receptor =  new TimelineReceiver();
        registerReceiver(receptor, filtro);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(receptor);
    }

    /**
     * Metodo que Crea un IntentService para llamar al servicio que lista las materias.
     */
    private void getAllMateriasOfertadas(){
        String idPrograma =  getIntent().getStringExtra("idPrograma");
        Log.d("REGISTRO -->", "CLASE: Oferta_Ppal   METODO: getAllMateriasOfertadas");
        Log.d("IMPORTANTE -->", "CLASE: Oferta_Ppal   METODO: getAllMateriasOfertadas codigo del programa enviado es: " +idPrograma);
        Intent listarMaterias = new Intent(Oferta_Ppal.this, ServiceImpl.class);
        listarMaterias.putExtra("accion", "listarMaterias");
        listarMaterias.putExtra("idPrograma", idPrograma);
        startService(listarMaterias);
    }

    class TimelineReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("REGISTRO -->", "CLASE: TimelineReciver   METODO: onReceive");
            MateriaOfertadaDao materiaOfertadaDao  = new MateriaOfertadaDaoImpl();
            List<MateriaOfertada> materiasOfertadas = materiaOfertadaDao.getAllMateriasOfertadas();
            Log.d("BROADCAST RECIBIDO", "onReceived");
            listaMaterias.setAdapter(new MateriaOfertadaListAdapter((Activity) context, (ArrayList<MateriaOfertada>) materiasOfertadas));
        }
    }
}
