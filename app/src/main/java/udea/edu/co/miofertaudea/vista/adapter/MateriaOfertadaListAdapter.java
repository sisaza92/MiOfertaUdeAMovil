package udea.edu.co.miofertaudea.vista.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import udea.edu.co.miofertaudea.R;
import udea.edu.co.miofertaudea.modelo.dto.MateriaOfertada;
import udea.edu.co.miofertaudea.util.ContextProvider;
import udea.edu.co.miofertaudea.vista.activity.GrupoActivity;

/**
 * Created by CristianCamilo on 10/10/2016.
 */

public class MateriaOfertadaListAdapter extends RecyclerView.Adapter<MateriaOfertadaListAdapter.MyViewHolder>  {

    private Activity activity;
    ArrayList<MateriaOfertada> materiaOfertadas;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView codigoMateria;
        public TextView nombreMateria;
        public TextView grupoMateria;
        public TextView creditos;
        public TextView horario;

        public MyViewHolder(View view) {
            super(view);

            // *** instanciamos a los recursos
            codigoMateria = (TextView) view.findViewById(R.id.tVMateriaValue);
            nombreMateria = (TextView) view.findViewById(R.id.tVApocopeValue);
            grupoMateria = (TextView) view.findViewById(R.id.tVGrupoValue);
            creditos = (TextView) view.findViewById(R.id.tVCreditosMateriaValue);
            horario = (TextView) view.findViewById(R.id.tVHorarioValue);
        }
    }
    public MateriaOfertadaListAdapter(Activity activity, ArrayList<MateriaOfertada> listaCanciones) {

        //super(activity, R.layout.materia_card);

        this.activity = activity;
        this.materiaOfertadas = listaCanciones;

    }

    public int getItemCount() {
        return materiaOfertadas.size();
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, final ViewGroup parent) {

        View view = null;

        LayoutInflater inflador = activity.getLayoutInflater();
        view = inflador.inflate(R.layout.materia_card, null);
        //final ViewHolder viewHolder = new ViewHolder();



        return view;
    }

    private View.OnClickListener getListener(final int position){
        Log.d("REGISTRO -->","CLASE: MateriaOfertadaListAdapter, METODO: getListener");
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listarGruposMateria = new Intent(ContextProvider.getContext(), GrupoActivity.class);
                String codigoMateria = materiaOfertadas.get(position).getCodigoMateria();
                String nombreMateria = materiaOfertadas.get(position).getNombreMateria();

                listarGruposMateria.putExtra("codigoMateria", codigoMateria);
                listarGruposMateria.putExtra("nombreMateria",nombreMateria);

                v.setOnClickListener(getListener(position));// TODO: mirar cual de los dos es el que funciona.
                Log.d("REGISTRO -->","ITEM "+position+" CLIQUEADO");
                Log.d("REGISTRO -->","se ha seleccionado la materia con nombre :"+nombreMateria+  " y codigoMateria: "
                        +codigoMateria+"para mostrar sus grupos");


                v.getContext().startActivity(listarGruposMateria);
            }
        };
        return listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.materia_card, parent, false);

        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, int position) {

        MateriaOfertada materiaOfertada = materiaOfertadas.get(position);
        // importante!!! establecemos el mensaje

        viewHolder.codigoMateria.setText(materiaOfertada.getCodigoMateria());
        viewHolder.nombreMateria.setText(materiaOfertada.getNombreMateria());
        viewHolder.grupoMateria.setText(materiaOfertada.getGrupo());
        viewHolder.creditos.setText(String.valueOf(materiaOfertada.getCreditos()));
        viewHolder.horario.setText(materiaOfertada.getHorario());

        viewHolder.itemView.setOnClickListener(getListener(position));

/*

        viewHolder.setOnClickListener(getListener(position));

        // loading album cover using Glide library
        Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });*/
    }
}
