package udea.edu.co.miofertaudea.util;


import android.content.Context;
import android.content.res.AssetManager;

import java.io.InputStream;
import java.util.Properties;
/**
 * Esta clase lee un archivo de propiedades lo carga y lo retorna para usar sus valores.
 * @author Created by CristianCamilo on 09/10/2016.
 */

public class PropertyReader {

    /**
     * Método que carga el archivo de propiedades especificado desde la carpeta assets.
     * @param file Nombre del archivo de propiedades ubicado en la carpeta assets.
     * @return Objeto de tipo Properties con el archivo de propiedades cargado.
     */
    public static Properties getMyProperties(String file) {

        Context context = ContextProvider.getContext();
        Properties properties = new Properties();

        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(file);
            properties.load(inputStream);

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

        return properties;
    }
}