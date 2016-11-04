package udea.edu.co.miofertaudea.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import udea.edu.co.miofertaudea.modelo.dao.Implementations.MateriaOfertadaDaoImpl;
import udea.edu.co.miofertaudea.modelo.dao.Implementations.ProgramaDaoImpl;
import udea.edu.co.miofertaudea.modelo.dao.Interfaces.MateriaOfertadaDao;
import udea.edu.co.miofertaudea.modelo.dao.Interfaces.ProgramaDao;
import udea.edu.co.miofertaudea.modelo.dto.MateriaOfertada;
import udea.edu.co.miofertaudea.modelo.dto.Programa;

/**
 * En esta clase se define que acciones se realizarán cuando sea consumido un servicio, ademas de
 * seleccionar cual de ellos se ejecutará.
 * @author  Created by CristianCamilo on 10/10/2016.
 */
public class ServiceImpl extends IntentService {

    private Handler mHandler;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("REGISTRO -->"," Clase: ServiceImpl Metodo: onCreate");
        mHandler = new Handler();
    }

    public ServiceImpl() {
        super("ServiceImpl");
        Log.d("REGISTRO -->"," Clase: ServiceImpl Metodo: constructor");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("REGISTRO -->"," Clase: ServiceImpl Metodo: onHandleIntent");
        if (intent != null) {
            String accion = intent.getStringExtra("accion");
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if ((networkInfo != null) && (networkInfo.isConnected())) {
                switch (accion){
                    case "listarMaterias":
                        listarMaterias();
                        break;
                    case "listarProgramas":
                        listarProgramas();
                        break;

                    case "":

                        break;
                }
            } else {
                // si no hay conexion muestra un mensaje avisando que no se realizó la sincronización.
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ServiceImpl.this, "No hay conexión a internet.", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }

    private void listarMaterias() {
            Log.d("REGISTRO -->"," CLASE: ServiceImpl METODO: listarMaterias");
            ServiceFactory.getClienteRest().obtenerMateriasOfertadas("101700", new Callback<List<MateriaOfertada>>() {
                @Override
                public void success(List<MateriaOfertada> materiasOfertadas, Response response) {
                    //TODO quitar for
                    for(MateriaOfertada materia:materiasOfertadas){
                        Log.d("REGISTRO -->",materia.toString());
                    }
                    MateriaOfertadaDao materiasOfertadasDao  = new MateriaOfertadaDaoImpl();

                    materiasOfertadasDao.saveAllMaterias(materiasOfertadas);
                    int size = materiasOfertadas.size();
                    Toast.makeText(ServiceImpl.this, "Materias Recibidas Exitosamente", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent("udea.edu.co.miofertaudea.NUEVA_LISTA");
                    //intent.putExtra("lista",materias);
                    sendBroadcast(intent);
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d("ERROR: ","Fallo al obtener materias");
                    Toast.makeText(ServiceImpl.this, "Fallo al Obtener las Materias", Toast.LENGTH_LONG).show();
                }
            });
    }

    private void listarProgramas() {
        Log.d("REGISTRO -->"," CLASE: ServiceImpl METODO: listarProgramas");
        ServiceFactory.getClienteRest().obtenerProgramaYUltimoSemestre("101700", new Callback<List<Programa>>() {
            @Override
            public void success(List<Programa> programas, Response response) {
                //TODO quitar for
                for(Programa programa:programas){
                    Log.d("REGISTRO -->",programa.toString());
                }

                ProgramaDao programaDao = new ProgramaDaoImpl();

                programaDao.saveProgramas(programas);
                int size = programas.size();
                Toast.makeText(ServiceImpl.this, "Programas Recibidos Exitosamente", Toast.LENGTH_LONG).show();
                Intent intent = new Intent("udea.edu.co.miofertaudea.NUEVA_LISTA");
                //intent.putExtra("lista",materias);
                sendBroadcast(intent);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("ERROR: ","Fallo al obtener los programas");
                Toast.makeText(ServiceImpl.this, "Fallo al Obtener los programas", Toast.LENGTH_LONG).show();
            }
        });
    }

}
