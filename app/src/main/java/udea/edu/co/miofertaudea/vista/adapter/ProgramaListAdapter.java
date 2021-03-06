package udea.edu.co.miofertaudea.vista.adapter;

import android.app.Activity;
import android.content.Intent;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import udea.edu.co.miofertaudea.R;
import udea.edu.co.miofertaudea.modelo.dto.Estudiante;
import udea.edu.co.miofertaudea.modelo.dto.Programa;
import udea.edu.co.miofertaudea.service.ServiceImpl;
import udea.edu.co.miofertaudea.vista.activity.Oferta_Ppal;

/**
 * Created by Santiago on 02/11/2016.
 */
public class ProgramaListAdapter extends ArrayAdapter<Programa>{

    private Activity activity;
    private ArrayList<Programa> listaProgramas;
    private Estudiante estudiante;
    private Long semestreAcademico;

    public ProgramaListAdapter(Activity activity, ArrayList<Programa> programas , Estudiante estudiante,Long semestreAcademico) {

        super(activity, R.layout.item_programa);
        this.activity = activity;
        this.listaProgramas = programas;
        this.estudiante = estudiante;
        this.semestreAcademico = semestreAcademico;
    }

    static class ViewHolder {
        TextView infoPrograma;
    }

    public int getCount() {
        return listaProgramas.size();
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, final ViewGroup parent) {

        Log.d("REGISTRO -->","CLASE: ProgramaListAdapter, METODO: getView");
        View view = null;
        Programa programa  = listaProgramas.get(position);
        LayoutInflater inflador = activity.getLayoutInflater();
        view = inflador.inflate(R.layout.item_programa, null);
        final ViewHolder viewHolder = new ViewHolder();

        // *** instanciamos a los recursos
        viewHolder.infoPrograma = (TextView) view.findViewById(R.id.textViewPrograma);

        // importante!!! establecemos el mensaje
        viewHolder.infoPrograma.setText(programa.toString());

        //escuchador de evento
        view.setOnClickListener(getListener(position));
        return view;
    }

    private View.OnClickListener getListener(final int position){
        Log.d("REGISTRO -->","CLASE: ProgramaListAdapter, METODO: getListener");
        View.OnClickListener listener = new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent listarMaterias = new Intent(getContext(), Oferta_Ppal.class);
                Programa program = listaProgramas.get(position);

                Log.d("REGISTRO -->","CLASE: ProgramaListAdapter, METODO: getListener se tiene el estudiante: "+
                estudiante.toString());

                if(!program.getEstado().equals("Activo")){

                    Toast.makeText(v.getContext(), "No se tiene oferta en los programas Cancelados", Toast.LENGTH_LONG).show();
                }
                else {

                    // se agrega la informacion a enviar a la actividad
                    listarMaterias.putExtra("ESTUDIANTE", estudiante);
                    listarMaterias.putExtra("PROGRAMA", program);
                    listarMaterias.putExtra("semestreAcademico", semestreAcademico);

                    // se inicia la otra actividad
                    v.getContext().startActivity(listarMaterias);
                }

                v.setOnClickListener(getListener(position));// TODO: mirar cual de los dos es el que funciona.
                Log.d("REGISTRO -->","ITEM "+position+" CLIQUEADO");
                //v.setBackgroundResource(R.color.colorPrimaryDark);

            }
        };
        return listener;
    }

}