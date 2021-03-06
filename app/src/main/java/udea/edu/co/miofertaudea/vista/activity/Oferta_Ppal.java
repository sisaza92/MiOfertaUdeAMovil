package udea.edu.co.miofertaudea.vista.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import udea.edu.co.miofertaudea.R;
import udea.edu.co.miofertaudea.modelo.dao.Implementations.ImpedimentoDaoImpl;
import udea.edu.co.miofertaudea.modelo.dao.Implementations.MateriaOfertadaDaoImpl;
import udea.edu.co.miofertaudea.modelo.dao.Implementations.TandaDaoImpl;
import udea.edu.co.miofertaudea.modelo.dao.Interfaces.ImpedimentoDao;
import udea.edu.co.miofertaudea.modelo.dao.Interfaces.MateriaOfertadaDao;
import udea.edu.co.miofertaudea.modelo.dao.Interfaces.TandaDao;
import udea.edu.co.miofertaudea.modelo.dto.Estudiante;
import udea.edu.co.miofertaudea.modelo.dto.Impedimento;
import udea.edu.co.miofertaudea.modelo.dto.MateriaOfertada;
import udea.edu.co.miofertaudea.modelo.dto.Programa;
import udea.edu.co.miofertaudea.modelo.dto.Tanda;
import udea.edu.co.miofertaudea.service.ServiceImpl;
import udea.edu.co.miofertaudea.vista.adapter.ImpedimentoListAdapter;
import udea.edu.co.miofertaudea.vista.adapter.MateriaOfertadaListAdapter;

public class Oferta_Ppal extends AppCompatActivity {

    private RecyclerView recyclerView;
    private IntentFilter filtroMaterias,filtroTanda,filtroImpedimento;
    private BroadcastReceiver receptorMaterias,receptorTanda,receptorImpedimento;

    private TextView mtVOfertaPPName;

    private TextView mTVOfertaPPImpedimentos;



    //TextView para la tanda
    private TextView mTVOfertaPPTanda;



    private Estudiante estudiante;
    private Long semestreAcademico;
    private Programa programa;
    private List<Impedimento> impedimentos;

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
        filtroImpedimento = new IntentFilter("udea.edu.co.miofertaudea.NUEVA_LISTA_IMPEDIMENTOS");
        receptorImpedimento =  new TimelineReceiverImpedimentos();
        registerReceiver(receptorImpedimento, filtroImpedimento);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mTVOfertaPPImpedimentos = (TextView) findViewById(R.id.tVImpedimentos);
        mtVOfertaPPName = (TextView) findViewById(R.id.tVOfertaPPName);

        mTVOfertaPPTanda = (TextView) findViewById(R.id.tVTanda);


        estudiante =(Estudiante) getIntent().getExtras().getSerializable("ESTUDIANTE");
        programa = (Programa) getIntent().getExtras().getSerializable("PROGRAMA");
        semestreAcademico =  getIntent().getLongExtra("semestreAcademico",0);

        Log.d("REGISTRO -->","CLASE: Oferta_Ppal      METODO: onCreate ----> en el intent" +
                " llego el estudiante: "+ estudiante.toString());
        Log.d("REGISTRO -->","CLASE: Oferta_Ppal      METODO: onCreate ----> en el intent" +
                " llego el programa: "+ programa.toString());
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
        //unregisterReceiver(receptorTanda);
        //unregisterReceiver(receptorImpedimento);
        //unregisterReceiver(receptorMaterias);
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
            Long machete = programa.getCodigoPrograma();
            String idPrograma = machete.toString();
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

            Intent obtenerTanda = new Intent(Oferta_Ppal.this, ServiceImpl.class);
            obtenerTanda.putExtra("accion", "obtenerTanda");
            obtenerTanda.putExtra("cedulaEstudiante",estudiante.getCedula());
            obtenerTanda.putExtra("semestreAcademico",semestreAcademico);

            startService(obtenerTanda);

    }

    /**
     * Metodo que Crea un IntentService para llamar al servicio que obtine la informacion de la tanda del estudiante
     */
    private void getImpedimentos(){

        Intent obtenerImpedimentos = new Intent(Oferta_Ppal.this, ServiceImpl.class);
        obtenerImpedimentos.putExtra("accion", "obtenerImpedimentos");
        obtenerImpedimentos.putExtra("cedulaEstudiante",estudiante.getCedula());
        obtenerImpedimentos.putExtra("codigoPrograma",programa.getCodigoPrograma());

        startService(obtenerImpedimentos);

    }

    public void setImpedimentos(List<Impedimento> impedimentos) {
        this.impedimentos = impedimentos;
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
                mTVOfertaPPTanda.setText(tanda.toString());

                getImpedimentos();

            }else{
                Log.d("CRITICO-->", "CLASE: TimelineReceiverTanda" +
                        "   METODO: NO ESTA TRAYENDO TANDA DE LA BD");
            }
        }
    }

    class TimelineReceiverImpedimentos extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("REGISTRO -->", "CLASE: TimelineReceiverImpedimentos   METODO: onReceive");

            ImpedimentoDao impedimentoDao = new ImpedimentoDaoImpl();
            List<Impedimento> impedimentos = impedimentoDao.getImpedimentos();

            if (impedimentos.size()>0){
                mTVOfertaPPImpedimentos.setVisibility(View.VISIBLE);
            }else{
                mTVOfertaPPImpedimentos.setVisibility(View.INVISIBLE);
            }
            setImpedimentos(impedimentos);
            getAllMateriasOfertadas();
        }
    }

    public void onClickShowImpedimentos(View view){

        ImpedimentoListAdapter impedimentoListAdapter = new ImpedimentoListAdapter(this, (ArrayList<Impedimento>) impedimentos);
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(Oferta_Ppal.this);

        builderSingle.setTitle("Impedimentos de matricula");
        builderSingle.setAdapter(impedimentoListAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builderSingle.show();
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
