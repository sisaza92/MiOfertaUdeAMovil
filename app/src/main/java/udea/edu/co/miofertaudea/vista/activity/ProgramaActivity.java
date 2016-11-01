package udea.edu.co.miofertaudea.vista.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;

import udea.edu.co.miofertaudea.R;

/**
 * Created by Santiago on 01/11/2016.
 */
public class ProgramaActivity extends AppCompatActivity {

    private IntentFilter filtro;
    private BroadcastReceiver receptor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prueba_programa_layout);


        Log.d("REGISTRO -->","CLASE: ProgramaActivity    METODO: onCreate");
    }
}
