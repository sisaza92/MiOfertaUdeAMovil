package udea.edu.co.miofertaudea.service;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import udea.edu.co.miofertaudea.modelo.dto.Estudiante;
import udea.edu.co.miofertaudea.modelo.dto.Grupo;
import udea.edu.co.miofertaudea.modelo.dto.Impedimento;
import udea.edu.co.miofertaudea.modelo.dto.MateriaOfertada;
import udea.edu.co.miofertaudea.modelo.dto.Programa;
import udea.edu.co.miofertaudea.modelo.dto.Tanda;

/**
 * Esta clase es la encargada de definir los servicios que serán consumidos, el metodo por el cual
 * se consumiran, la url de acceso a los mismos y los parámetros necesarios para su consumo.
 * Created by CristianCamilo on 09/10/2016.
 */
public interface ServiceInterface  {

    final String URL_CONTEXT_PATH = "/rest";
    final String URL_SERVICE = "/MiOferta";

    /**
     * Retorna los programas del usuario y su ultimo semestre, no está creado,
     * deberá retornar la estructura: programa, nombreprograma, estado, semestre
     */
    @GET(URL_CONTEXT_PATH+URL_SERVICE+"/obtenerProgramaYUltimoSemestre/{cedulaEstudiante}")
    public void obtenerProgramaYUltimoSemestre(@Path("cedulaEstudiante") String cedulaEstudiante, Callback<List<Programa>> callback);

    /**
     * Retorna las materias que está cursando en el programa/semestre, no está creado,
     * deberá retornar la estructura: codigomateria, nombremateria, creditos, grupo, horario
     */
    @GET(URL_CONTEXT_PATH+URL_SERVICE+"/obtenerMateriasOfertadas/{cedulaEstudiante}/{idPrograma}")
    public void obtenerMateriasOfertadas(@Path("cedulaEstudiante") String cedulaEstudiante,
                                         @Path("idPrograma") String idPrograma, Callback<List<MateriaOfertada>> callback);

    /**
     * Retorna los grupos disponibles de la materia el programa/semestre, no está creado,
     * deberá retornar la estructura: grupo, cupomaximo, cupodisponible, aula, horario, nombreprofesor
     */
    @GET(URL_CONTEXT_PATH+URL_SERVICE+"/obtenerGrupos/{codigoMateria}")
    public void obtenerGrupos(@Path("codigoMateria") String codigoMateria, Callback<List<Grupo>> callback);

    /**
     * Retorna la tanda en el programa/semestre, no está creado, deberá retornar
     * la estructura: numerotanda, fecha, hora
     */
    @GET(URL_CONTEXT_PATH+URL_SERVICE+"/obtenerTanda/{cedulaEstudiante}")
    public void obtenerTanda(Callback<Tanda> callback);

    /**
     * Retorna los impedimientos el programa, no está creado, deberá retornar la
     * estructura: semestre, impedimiento
     */
    @GET(URL_CONTEXT_PATH+URL_SERVICE+"/obtenerImpedimentos/{cedulaEstudiante}")
    public void obtenerImpedimentos(Callback<List<Impedimento>> impedimentos);

    /**
     * Retorna  el un objeto Estudiante en formato json, no está creado, deberá retornar la
     * estructura:
     */
    @GET(URL_CONTEXT_PATH+URL_SERVICE+"/obtenerInfoEstudiante/{cedulaEstudiante}")
    public void obtenerEstudiante(@Path("cedulaEstudiante") String cedula,Callback<Estudiante> estudiante);




}
