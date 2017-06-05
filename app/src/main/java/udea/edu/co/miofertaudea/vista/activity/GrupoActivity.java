package udea.edu.co.miofertaudea.vista.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    private RecyclerView recyclerView;
    private IntentFilter filtro;
    private BroadcastReceiver receptor;

//    RecyclerView listaGrupos;
    TextView cabecera;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("REGISTRO -->", "CLASE: GrupoActivity    METODO: onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo_layout);
        initCollapsingToolbar();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_viewGrupos);
        cabecera = (TextView)findViewById(R.id.textViewCabeceraGrupo);
        filtro = new IntentFilter("udea.edu.co.miofertaudea.NUEVA_LISTA_GRUPOS");

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

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
    @Override
    public void onPause() {
        super.onPause();
        //unregisterReceiver(receptor);

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

            recyclerView.setAdapter(new GrupoListAdapter((Activity) context, (ArrayList<Grupo>) grupos));

        }
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle("Grrupos Disponibles");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle("");
                    isShow = false;
                }
            }
        });
    }
}
