 package udea.edu.co.miofertaudea.vista.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import udea.edu.co.miofertaudea.R;
import udea.edu.co.miofertaudea.modelo.dto.Programa;
import udea.edu.co.miofertaudea.service.ServiceImpl;

/**
 * Created by Santiago on 02/11/2016.
 */
public class ProgramaSpinnerAdapter extends ArrayAdapter<Programa>{

    private Activity activity;
    ArrayList<Programa> listaProgramas;

    public ProgramaSpinnerAdapter(Activity activity, ArrayList<Programa> programas) {

     super(activity, R.layout.item_programa);
        this.activity = activity;
        this.listaProgramas = programas;
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

        View view = null;
        Programa programa  = listaProgramas.get(position);
        LayoutInflater inflador = activity.getLayoutInflater();
        view = inflador.inflate(R.layout.item_programa, null);
        final ViewHolder viewHolder = new ViewHolder();

        // *** instanciamos a los recursos
        viewHolder.infoPrograma = (TextView) view.findViewById(R.id.programa);

        // importante!!! establecemos el mensaje
        viewHolder.infoPrograma.setText(programa.toString());
        view.setOnClickListener(getListener(position));
        return view;
    }

    private View.OnClickListener getListener(final int position){
        Log.d("REGISTRO -->","CLASE: ProgramaSpinnerAdapter, METODO: getListener");
        View.OnClickListener listener = new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent listarProgramas = new Intent(getContext(),ServiceImpl.class);
                String idPrograma = "" + listaProgramas.get(position).getCodigoPrograma();
                listarProgramas.putExtra("idPrograma", idPrograma);
                v.getContext().startService(listarProgramas);
                v.setOnClickListener(getListener(position));// TODO: mirar cual de los dos es el que funciona.
                //v.setBackgroundResource(R.color.colorPrimaryDark);
                Log.d("REGISTRO -->","ITEM "+position+" CLIQUEADO");
            }
        };
        return listener;
    }

}
