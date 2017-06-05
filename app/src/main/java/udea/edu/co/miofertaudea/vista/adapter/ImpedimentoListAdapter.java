package udea.edu.co.miofertaudea.vista.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import udea.edu.co.miofertaudea.R;
import udea.edu.co.miofertaudea.modelo.dto.Impedimento;

/**
 * Created by Isaza on 04/06/2017.
 */

public class ImpedimentoListAdapter extends ArrayAdapter<Impedimento> {

    private Activity activity;
    private ArrayList<Impedimento> listaImpedimentos;

    public ImpedimentoListAdapter(Activity activity, ArrayList<Impedimento> impedimentos) {

        super(activity, R.layout.item_impedimento);
        this.activity = activity;
        this.listaImpedimentos = impedimentos;
    }

    static class ViewHolder {
        TextView semestre;
        TextView tipo;
        TextView nombre;
    }

    public int getCount() {
        return listaImpedimentos.size();
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, final ViewGroup parent) {

        Log.d("REGISTRO -->","CLASE: ImpedimentoListAdapter, METODO: getView");
        View view = null;
        Impedimento impedimento  = listaImpedimentos.get(position);
        LayoutInflater inflador = activity.getLayoutInflater();
        view = inflador.inflate(R.layout.item_impedimento, null);
        final ViewHolder viewHolder = new ViewHolder();

        // *** instanciamos a los recursos
        viewHolder.semestre = (TextView) view.findViewById(R.id.semestre);
        viewHolder.tipo = (TextView) view.findViewById(R.id.tipo);
        viewHolder.nombre = (TextView) view.findViewById(R.id.nombre);

        // importante!!! establecemos el mensaje
        viewHolder.semestre.setText("Semestre: "+impedimento.getSemestre().toString());
        viewHolder.tipo.setText("Tipo: "+impedimento.getTipo());
        viewHolder.nombre.setText("Impedimento: "+impedimento.getNombre());


        return view;
    }
}
