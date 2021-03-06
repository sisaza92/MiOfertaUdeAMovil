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
import udea.edu.co.miofertaudea.modelo.dao.Implementations.EstudianteDaoImpl;
import udea.edu.co.miofertaudea.modelo.dao.Implementations.GrupoDaoImpl;
import udea.edu.co.miofertaudea.modelo.dao.Implementations.ImpedimentoDaoImpl;
import udea.edu.co.miofertaudea.modelo.dao.Implementations.MateriaOfertadaDaoImpl;
import udea.edu.co.miofertaudea.modelo.dao.Implementations.ProgramaDaoImpl;
import udea.edu.co.miofertaudea.modelo.dao.Implementations.TandaDaoImpl;
import udea.edu.co.miofertaudea.modelo.dao.Interfaces.EstudianteDao;
import udea.edu.co.miofertaudea.modelo.dao.Interfaces.GrupoDao;
import udea.edu.co.miofertaudea.modelo.dao.Interfaces.ImpedimentoDao;
import udea.edu.co.miofertaudea.modelo.dao.Interfaces.MateriaOfertadaDao;
import udea.edu.co.miofertaudea.modelo.dao.Interfaces.ProgramaDao;
import udea.edu.co.miofertaudea.modelo.dao.Interfaces.TandaDao;
import udea.edu.co.miofertaudea.modelo.dto.Estudiante;
import udea.edu.co.miofertaudea.modelo.dto.Grupo;
import udea.edu.co.miofertaudea.modelo.dto.Impedimento;
import udea.edu.co.miofertaudea.modelo.dto.MateriaOfertada;
import udea.edu.co.miofertaudea.modelo.dto.Programa;
import udea.edu.co.miofertaudea.modelo.dto.Tanda;

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
                String cedulaEstudiante;

                switch (accion){
                    case "listarMaterias":
                        String idPrograma = intent.getStringExtra("idPrograma");
                        //alerta el idEstudiante estallegando null
                        //String idEstudiante = intent.getStringExtra("idEstudiante");
                        Log.d("IMPORTANTE -->","Al servicio listarMaterias le ha llegado el idPrograma: "
                                +idPrograma);
                        listarMaterias(); // idPrograma en el metodo
                        break;
                    case "listarProgramas":
                        cedulaEstudiante = intent.getStringExtra("cedulaEstudiante");
                        listarProgramas(cedulaEstudiante);
                        break;

                    case "listarGrupos":
                        String codigoMateria = intent.getStringExtra("codigoMateria");
                        Log.d("IMPORTANTE -->","Al servicio listarGrupos le ha llegado el idMateria: "
                                +codigoMateria);
                        listarGruposMaterias(codigoMateria);
                        break;
                    case "obtenerInfoEstudiante":
                        cedulaEstudiante = intent.getStringExtra("cedulaEstudiante");
                        Log.d("IMPORTANTE -->","Al servicio obtenerInfoEstudiante le ha llegado la cedulaEstudiante: "
                                +cedulaEstudiante);
                        obtenerEstudiante(cedulaEstudiante);

                    break;
                    case "obtenerTanda":
                        cedulaEstudiante = intent.getStringExtra("cedulaEstudiante");
                        Long semestre =  intent.getLongExtra("semestreAcademico",0);
                        Log.d("IMPORTANTE -->","Al servicio obtenerTanda le ha llegado la cedulaEstudiante: "
                                +cedulaEstudiante);
                        Log.d("IMPORTANTE -->","Al servicio obtenerTanda le ha llegado el semestre: "
                                +semestre);

                        obtenerTanda(cedulaEstudiante, semestre);

                        break;

                    case "obtenerImpedimentos":
                        cedulaEstudiante = intent.getStringExtra("cedulaEstudiante");
                        Long programa =  intent.getLongExtra("codigoPrograma",0);
                        Log.d("IMPORTANTE -->","Al servicio obtenerImpedimentos le ha llegado la cedulaEstudiante: "
                                +cedulaEstudiante);
                        Log.d("IMPORTANTE -->","Al servicio obtenerTanda le ha llegado el codigoPrograma: "
                                +programa);

                        obtenerImpedimentos(cedulaEstudiante, programa);

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

    private void listarMaterias() {  //final String idPrograma en el metodo
            Log.d("REGISTRO -->"," CLASE: ServiceImpl METODO: listarMaterias");
            ServiceFactory.getClienteRest().obtenerMateriasOfertadas("101700","504", new Callback<List<MateriaOfertada>>() {
                @Override
                public void success(List<MateriaOfertada> materiasOfertadas, Response response) {
                    //TODO quitar for
                    for(MateriaOfertada materia:materiasOfertadas){
                        Log.d("REGISTRO -->",materia.toString());
                    }
                    MateriaOfertadaDao materiasOfertadasDao  = new MateriaOfertadaDaoImpl();

                    materiasOfertadasDao.saveAllMaterias(materiasOfertadas);
                    int size = materiasOfertadas.size();
                    //Toast.makeText(ServiceImpl.this, "Materias Recibidas Exitosamente", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent("udea.edu.co.miofertaudea.NUEVA_LISTA_MATERIAS");
                    intent.putExtra("BroadcastType","Materias");
                    sendBroadcast(intent);
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d("ERROR: ","Fallo al obtener materias");
                    Toast.makeText(ServiceImpl.this, "Fallo al Obtener las Materias", Toast.LENGTH_LONG).show();
                }
            });
    }

    private void listarProgramas(String cedula) {
        Log.d("REGISTRO -->"," CLASE: ServiceImpl METODO: listarProgramas");
        ServiceFactory.getClienteRest().obtenerProgramas(cedula, new Callback<List<Programa>>() {
            @Override
            public void success(List<Programa> programas, Response response) {
                //TODO quitar for
                for(Programa programa:programas){
                    Log.d("REGISTRO -->",programa.toString());
                }

                ProgramaDao programaDao = new ProgramaDaoImpl();

                programaDao.saveProgramas(programas);
                int size = programas.size();
                //Toast.makeText(ServiceImpl.this, "Programas Recibidos Exitosamente", Toast.LENGTH_LONG).show();
                Intent intent = new Intent("udea.edu.co.miofertaudea.NUEVA_LISTA_PROGRAMAS");
                sendBroadcast(intent);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("ERROR: ","Fallo al obtener los programas");
                Toast.makeText(ServiceImpl.this, "Fallo al Obtener los programas", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void listarGruposMaterias(final String codigoMateria) {
        Log.d("REGISTRO -->"," CLASE: ServiceImpl METODO: listarGruposMaterias");
        ServiceFactory.getClienteRest().obtenerGrupos(codigoMateria, new Callback<List<Grupo>>() {
            @Override
            public void success(List<Grupo> grupos, Response response) {
                //TODO quitar for
                for(Grupo grupo:grupos){
                    Log.d("REGISTRO -->",grupo.toString());
                }
                GrupoDao grupoDao = new GrupoDaoImpl();

                grupoDao.saveAllGrupos(grupos,codigoMateria);
                int size = grupos.size();
                //Toast.makeText(ServiceImpl.this, "Grupos Recibidos Exitosamente", Toast.LENGTH_LONG).show();
                Intent intent = new Intent("udea.edu.co.miofertaudea.NUEVA_LISTA_GRUPOS");
                intent.putExtra("codigoMateria",codigoMateria);
                sendBroadcast(intent);

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("ERROR: ","Fallo al obtener los grupos de la materia");
                Toast.makeText(ServiceImpl.this, "Fallo al Obtener los grupos de la materia", Toast.LENGTH_LONG).show();

            }
        });


        }

    private void obtenerEstudiante(String cedulaEstudiante) {
        Log.d("REGISTRO -->"," CLASE: ServiceImpl METODO: obtenerInfoEstudiante");
        ServiceFactory.getClienteRest().obtenerInfoEstudiante(cedulaEstudiante, new Callback<Estudiante>(){
            @Override
            public void success(Estudiante estudiante, Response response) {
                //TODO quitar for
                Log.d("REGISTRO -->"," CLASE: ServiceImpl METODO: obtenerInfoEstudiante obtiene" +
                       " el estudiante: " +estudiante.toString());


                EstudianteDao estudianteDao = new EstudianteDaoImpl();

                estudianteDao.saveEstudiante(estudiante);
                //Toast.makeText(ServiceImpl.this, "Estudiante Recibido Exitosamente", Toast.LENGTH_LONG).show();
                Intent intent = new Intent("udea.edu.co.miofertaudea.NUEVO_ESTUDIANTE");
                intent.putExtra("cedulaEstudiante",estudiante.getCedula());
                sendBroadcast(intent);

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("ERROR: ","Fallo al obtener la informacion del estudiante");
                Toast.makeText(ServiceImpl.this, "Fallo al Obtener la informacion del estudiante", Toast.LENGTH_LONG).show();


            }
        });
    }

    private void obtenerTanda(String cedulaEstudiante, Long semestre) {
        Log.d("REGISTRO -->"," CLASE: ServiceImpl METODO: obtenerTanda");
        ServiceFactory.getClienteRest().obtenerTanda(cedulaEstudiante, semestre, new Callback<Tanda>(){
            @Override
            public void success(Tanda tanda, Response response) {
                //TODO quitar for
                Log.d("REGISTRO -->"," CLASE: ServiceImpl METODO: obtenerTanda obtiene" +
                        " la tanda: " +tanda.toString());

                //puede que no este guardando bien
                TandaDao tandaDao = new TandaDaoImpl();

                tandaDao.saveTanda(tanda);
                //Toast.makeText(ServiceImpl.this, "Tanda Recibida Exitosamente", Toast.LENGTH_LONG).show();
                Intent intent = new Intent("udea.edu.co.miofertaudea.NUEVA_TANDA");
                intent.putExtra("BroadcastType","Tanda");
                //intent.putExtra("cedulaEstudiante",tanda.getCedula());
                sendBroadcast(intent);

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("ERROR: ","Fallo al obtener la Tanda de matricula");
                Toast.makeText(ServiceImpl.this, "Fallo al Obtener la Tanda de matricula", Toast.LENGTH_LONG).show();


            }
        });
    }

    private void obtenerImpedimentos(String cedulaEstudiante, Long programa) {
        Log.d("REGISTRO -->"," CLASE: ServiceImpl METODO: obtenerImpedimentos");
        ServiceFactory.getClienteRest().obtenerImpedimentos(cedulaEstudiante, programa, new Callback<List<Impedimento>>() {
            @Override
            public void success(List<Impedimento> impedimentos, Response response) {

                for(Impedimento impedimento:impedimentos){
                    Log.d("REGISTRO -->",impedimento.toString());
                }
                ImpedimentoDao impedimentoDao = new ImpedimentoDaoImpl();

                impedimentoDao.saveImpedimentos(impedimentos);
                int size = impedimentos.size();
                //Toast.makeText(ServiceImpl.this, "Impedimentos Recibidos Exitosamente", Toast.LENGTH_LONG).show();
                Intent intent = new Intent("udea.edu.co.miofertaudea.NUEVA_LISTA_IMPEDIMENTOS");
                sendBroadcast(intent);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("ERROR: ","Fallo al obtener los  Impedimentos de matricula");
                Toast.makeText(ServiceImpl.this, "Fallo al obtener los  Impedimentos de matricula", Toast.LENGTH_LONG).show();


            }
        });
    }
}
