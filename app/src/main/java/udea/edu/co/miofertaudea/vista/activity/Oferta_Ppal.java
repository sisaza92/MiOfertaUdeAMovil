package udea.edu.co.miofertaudea.vista.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

    private RecyclerView recyclerView;
    private IntentFilter filtro;
    private BroadcastReceiver receptor;

    Fragment materiasFragment;
    ListView listaMaterias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        setContentView(R.layout.activity_oferta_ppal);
        initCollapsingToolbar();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


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
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
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
        MateriaOfertadaDao materiaOfertadaDao  = new MateriaOfertadaDaoImpl();
        List<MateriaOfertada> materiasOfertadas = materiaOfertadaDao.getAllMateriasOfertadas();
        if(materiasOfertadas.size()>0){
            //listaMaterias.setAdapter(new MateriaOfertadaListAdapter( this, (ArrayList<MateriaOfertada>) materiasOfertadas));
            recyclerView.setAdapter(new MateriaOfertadaListAdapter( this, (ArrayList<MateriaOfertada>) materiasOfertadas));
        }else{
            String idPrograma =  getIntent().getStringExtra("idPrograma");
            String idEstudiante = getIntent().getStringExtra("idEstudiante");
            Log.d("REGISTRO -->", "CLASE: Oferta_Ppal   METODO: getAllMateriasOfertadas");
            Log.d("IMPORTANTE -->", "CLASE: Oferta_Ppal   METODO: getAllMateriasOfertadas" +
                    " codigo del programa enviado es: " +idPrograma+ "  y el idEstudiante enviado es: "+idEstudiante);
            Intent listarMaterias = new Intent(Oferta_Ppal.this, ServiceImpl.class);
            listarMaterias.putExtra("accion", "listarMaterias");
            listarMaterias.putExtra("idPrograma",idPrograma);
            startService(listarMaterias);
        }
    }

    class TimelineReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d("REGISTRO -->", "CLASE: TimelineReciver   METODO: onReceive");
            MateriaOfertadaDao materiaOfertadaDao  = new MateriaOfertadaDaoImpl();
            List<MateriaOfertada> materiasOfertadas = materiaOfertadaDao.getAllMateriasOfertadas();
            Log.d("BROADCAST RECIBIDO", "onReceived");
            recyclerView.setAdapter(new MateriaOfertadaListAdapter( (Activity) context, (ArrayList<MateriaOfertada>) materiasOfertadas));

           // listaMaterias.setAdapter(new MateriaOfertadaListAdapter((Activity) context, (ArrayList<MateriaOfertada>) materiasOfertadas));
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
                    collapsingToolbar.setTitle("HOLAAAAA");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle("CRISSSSS ");
                    isShow = false;
                }
            }
        });
    }
}
