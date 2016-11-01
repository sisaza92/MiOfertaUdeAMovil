package udea.edu.co.miofertaudea.vista.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;

import udea.edu.co.miofertaudea.R;

/**
 * Created by Santiago on 01/11/2016.
 */
public class ProgramaActivity extends AppCompatActivity {

    private IntentFilter filtro;
    private BroadcastReceiver receptor;
    private Spinner spinerProgramas;
    private ArrayList<String>lista = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prueba_programa_layout);
        // spinerProgramas se utiliza para setear el spiner del layout
        spinerProgramas= (Spinner) findViewById(R.id.spinner_programa);
        spinerProgramas.setAdapter();
        spinerProgramas.setOnItemClickListener((AdapterView.OnItemClickListener) this);

        Log.d("REGISTRO -->","CLASE: ProgramaActivity    METODO: onCreate");
    }
}
