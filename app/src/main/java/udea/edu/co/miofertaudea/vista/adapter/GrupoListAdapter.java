package udea.edu.co.miofertaudea.vista.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import udea.edu.co.miofertaudea.R;
import udea.edu.co.miofertaudea.modelo.dto.Grupo;


/**
 * Created by Santiago on 18/11/2016.
 */
public class GrupoListAdapter extends ArrayAdapter<Grupo> {

    private Activity activity;
    ArrayList<Grupo> listaGrupos;

    public GrupoListAdapter(Activity activity, ArrayList<Grupo> grupos){
        super(activity, R.layout.item_grupo);
        this.activity = activity;
        this.listaGrupos = grupos;

    }

    static class ViewHolder {
        TextView grupoHorario;
        TextView cupos;
        TextView aula;
        TextView profesor;
    }

    public int getCount() {
        return listaGrupos.size();
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, final ViewGroup parent) {
        Log.d("REGISTRO -->","CLASE: GrupoListAdapter, METODO: getView");
        View view = null;
        Grupo grupo = listaGrupos.get(position);
        LayoutInflater inflador = activity.getLayoutInflater();
        view = inflador.inflate(R.layout.item_grupo,null);
        final ViewHolder viewHolder = new ViewHolder();

        // *** instanciamos a los recursos
        viewHolder.grupoHorario = (TextView) view.findViewById(R.id.textViewGrupoHorario);
        viewHolder.cupos = (TextView) view.findViewById(R.id.textViewCupos);
        viewHolder.aula = (TextView) view.findViewById(R.id.textViewAula);
        viewHolder.profesor = (TextView) view.findViewById(R.id.textViewProfesor);

        // importante!!! establecemos el mensaje
        viewHolder.grupoHorario.setText(grupo.toStringGrupoHorario());
        viewHolder.cupos.setText(grupo.toStringCupos());
        viewHolder.aula.setText(viewHolder.aula.getText()+grupo.getAula());
        viewHolder.profesor.setText(viewHolder.profesor.getText()+grupo.getNombreProfesor());

        return view;
    }


}
