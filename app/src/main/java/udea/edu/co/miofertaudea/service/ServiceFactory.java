package udea.edu.co.miofertaudea.service;


import android.util.Log;

import retrofit.RestAdapter;
import udea.edu.co.miofertaudea.util.PropertyReader;

/**
 * Esta clase es la encargada de crear un cliente de tipo REST que será el encargado de consumir
 * el servicio.
 * @author Created by CristianCamilo on 09/10/2016.
 */
public class ServiceFactory {

    private static ServiceInterface CLIENTE_REST;
    private static final String SERVICE_HOST = PropertyReader.getMyProperties("Config.properties").getProperty("SERVICE_HOST");
    private static final String SERVICE_PORT = PropertyReader.getMyProperties("Config.properties").getProperty("SERVICE_PORT");
    private static final String URL_ROOT = "http://"+SERVICE_HOST+":"+SERVICE_PORT;

    static {
        setupRestClient();
    }

    /**
     * Este método devuelve el adaptador de Retrofit asociado a una la interfaz de servicios.
     * @return Objeto de tipo ServiceInterface.
     */
    public static ServiceInterface getClienteRest() {
        Log.d("Clase: ServiceFactory","Metodo: getClienteRest");
        return CLIENTE_REST;
    }

    /**
     * Este metodo inicializa el adaptador de Retrofit asociado a una la interfaz de servicios.
     */
    private static void setupRestClient() {
        Log.d("Clase: ServiceFactory","Metodo: setupRestClient");
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(URL_ROOT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        CLIENTE_REST = restAdapter.create(ServiceInterface.class);
    }
}
