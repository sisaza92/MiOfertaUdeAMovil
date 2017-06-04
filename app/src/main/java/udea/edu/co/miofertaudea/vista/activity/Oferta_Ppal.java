package udea.edu.co.miofertaudea.vista.activity;

import android.app.Activity;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import udea.edu.co.miofertaudea.R;
import udea.edu.co.miofertaudea.modelo.dao.Implementations.MateriaOfertadaDaoImpl;
import udea.edu.co.miofertaudea.modelo.dao.Implementations.TandaDaoImpl;
import udea.edu.co.miofertaudea.modelo.dao.Interfaces.MateriaOfertadaDao;
import udea.edu.co.miofertaudea.modelo.dao.Interfaces.TandaDao;
import udea.edu.co.miofertaudea.modelo.dto.Estudiante;
import udea.edu.co.miofertaudea.modelo.dto.MateriaOfertada;
import udea.edu.co.miofertaudea.modelo.dto.Tanda;
import udea.edu.co.miofertaudea.service.ServiceImpl;
import udea.edu.co.miofertaudea.vista.adapter.MateriaOfertadaListAdapter;

public class Oferta_Ppal extends AppCompatActivity {

    private RecyclerView recyclerView;
    private IntentFilter filtroMaterias,filtroTanda,filtroImpedimento;
    private BroadcastReceiver receptorMaterias,receptorTanda,receptorImpedimento;


    private TextView mTVOfertaPPImpedimentos;
    private TextView mtVOfertaPPName;


    //TextView para la tanda
    private TextView mTVOfertaPPTandaFecha,mTVOfertaPPTandaHoraInicial,mTVOfertaPPTandaHoraFinal;



    private Estudiante estudiante;
    Long semestreAcademico;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("REGISTRO -->","CLASE: Oferta_Ppal      METODO: onCreate");
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        setContentView(R.layout.activity_oferta_ppal);
        initCollapsingToolbar();

        // filtroMaterias para el servicio que de las materias ofertadas
        filtroMaterias = new IntentFilter("udea.edu.co.miofertaudea.NUEVA_LISTA_MATERIAS");
        receptorMaterias =  new TimelineReceiverMaterias();
        registerReceiver(receptorMaterias, filtroMaterias);

        // filfro para el servicio de la tanda de matricula
        filtroTanda = new IntentFilter("udea.edu.co.miofertaudea.NUEVA_TANDA");
        receptorTanda =  new TimelineReceiverTanda();
        registerReceiver(receptorTanda, filtroTanda);

        // filfro para el servicio de los impedimentos de matricula
        //filtroImpedimento = new IntentFilter("udea.edu.co.miofertaudea.NUEVA_TANDA");
        //receptorImpedimento =  new TimelineReceiverTanda();
        //registerReceiver(receptorImpedimento, filtroImpedimento);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mTVOfertaPPImpedimentos = (TextView) findViewById(R.id.tVOfertaPPImpedimentos);
        mtVOfertaPPName = (TextView) findViewById(R.id.tVOfertaPPName);

        mTVOfertaPPTandaFecha = (TextView) findViewById(R.id.tVOfertaPPTandaFecha);
        mTVOfertaPPTandaHoraInicial = (TextView) findViewById(R.id.tVOfertaPPTandaHoraInicial);
        mTVOfertaPPTandaHoraFinal = (TextView) findViewById(R.id.tVOfertaPPTandaHoraFinal);


        estudiante =(Estudiante) getIntent().getExtras().getSerializable("ESTUDIANTE");
        semestreAcademico =  getIntent().getLongExtra("semestreAcademico",0);
        Log.d("REGISTRO -->","CLASE: Oferta_Ppal      METODO: onCreate ----> en el intent" +
                " llego el estudiante: "+ estudiante.toString());

        Log.d("REGISTRO -->","CLASE: Oferta_Ppal      METODO: onCreate ----> en el intent" +
                " llego el semestreAcaemico: "+ semestreAcademico);

        mtVOfertaPPName.setText("Estudiante: "+ estudiante.getNombres()+" "+estudiante.getApellidos());


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        getTanda();


        //getAllMateriasOfertadas();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(receptorMaterias);
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
            String idEstudiante = estudiante.getCedula();

            Log.d("REGISTRO -->", "CLASE: Oferta_Ppal   METODO: getAllMateriasOfertadas");
            Log.d("IMPORTANTE -->", "CLASE: Oferta_Ppal   METODO: getAllMateriasOfertadas" +
                    " codigo del programa enviado es: " +idPrograma+ "  y el idEstudiante enviado es: "+idEstudiante);
            Intent listarMaterias = new Intent(Oferta_Ppal.this, ServiceImpl.class);
            listarMaterias.putExtra("accion", "listarMaterias");
            listarMaterias.putExtra("idPrograma",idPrograma);
            startService(listarMaterias);
        }
    }

    /**
     * Metodo que Crea un IntentService para llamar al servicio que obtine la informacion de la tanda del estudiante
     */
    private void getTanda(){

        filtroMaterias = new IntentFilter("udea.edu.co.miofertaudea.NUEVA_TANDA");
            Intent obtenerTanda = new Intent(Oferta_Ppal.this, ServiceImpl.class);
            obtenerTanda.putExtra("accion", "obtenerTanda");
            obtenerTanda.putExtra("cedulaEstudiante",estudiante.getCedula());
            obtenerTanda.putExtra("semestreAcademico",semestreAcademico);

            startService(obtenerTanda);

    }

    class TimelineReceiverMaterias extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d("REGISTRO -->", "CLASE: TimelineReceiverMaterias   METODO: onReceive");

            MateriaOfertadaDao materiaOfertadaDao  = new MateriaOfertadaDaoImpl();
            List<MateriaOfertada> materiasOfertadas = materiaOfertadaDao.getAllMateriasOfertadas();
            Log.d("BROADCAST RECIBIDO", "onReceived");
            recyclerView.setAdapter(new MateriaOfertadaListAdapter( (Activity) context, (ArrayList<MateriaOfertada>) materiasOfertadas));
        }
    }

    class TimelineReceiverTanda extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("REGISTRO -->", "CLASE: TimelineReceiverTanda   METODO: onReceive");

            TandaDao tandaDao = new TandaDaoImpl();
            Tanda tanda = tandaDao.getTanda();

            if(tanda != null){
                Log.d("IMPORTANTE-->", "CLASE: TimelineReceiverTanda" +
                        "   METODO: SI ESTA TRAYENDO TANDA DE LA BD: " +tanda.toString());
                // logica cuando llegue la tanda
                mTVOfertaPPTandaFecha.setText(tanda.getFecha());
                mTVOfertaPPTandaHoraInicial.setText(tanda.toSringHoraInicial());
                mTVOfertaPPTandaHoraFinal.setText(tanda.toSringHoraFinal());
                getAllMateriasOfertadas();

            }else{
                Log.d("CRITICO-->", "CLASE: TimelineReceiverTanda" +
                        "   METODO: NO ESTA TRAYENDO TANDA DE LA BD");
            }
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
                    collapsingToolbar.setTitle("Materias Ofertadas");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle("");
                    isShow = false;
                }
            }
        });
    }
}
