package udea.edu.co.miofertaudea.vista.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import udea.edu.co.miofertaudea.R;

/**
 * Created by CristianCamilo on 12/10/2016.
 */
public class MateriasListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.lista_materias, container, false);
        //Nueva Regla: Este fragmento estara debajo del fragment de información académica
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, R.id.fragment_info_academica);
        view.setLayoutParams(params);


        return view;

    }
}
