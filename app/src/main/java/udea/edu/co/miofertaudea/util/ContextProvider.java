package udea.edu.co.miofertaudea.util;

import android.app.Application;
import android.content.Context;

/**
 * Esta clase devuelve un contexto para poder usarlo en clases que no heredan de activities como por
 * ejemplo el constructor del dbHelper.
 * Created by CristianCamilo on 21/09/2016.
 */
public class ContextProvider extends Application {

    private static ContextProvider instance;

    /**
     * Método Constructor de la clase.
     */
    public ContextProvider() {
        instance = this;
    }

    /**
     * Retorna el contexto actual de la aplicacion.
     * @return
     */
    public static Context getContext() {
        return instance;
    }
}
