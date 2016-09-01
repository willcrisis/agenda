package willcrisis.com.agenda;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import willcrisis.com.agenda.modelo.Prova;

public class ListaProvasFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_provas, container, false);

        List<String> topicosPortugues = Arrays.asList("Objeto direto", "Objeto indireto", "Sujeito");
        Prova provaPortugues = new Prova("Português", "31/08/2016", topicosPortugues);

        List<String> topicosMatematica = Arrays.asList("Trigonometria", "Álgebra");
        Prova provaMatematica = new Prova("Matematica", "01/09/2016", topicosMatematica);

        List<Prova> provas = Arrays.asList(provaPortugues, provaMatematica);

        ArrayAdapter<Prova> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, provas);
        ListView listaProvas = (ListView) view.findViewById(R.id.provas_lista);
        listaProvas.setAdapter(adapter);

        listaProvas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prova prova = (Prova) parent.getItemAtPosition(position);

                ProvasActivity activity = (ProvasActivity) getActivity();
                activity.selecionarProva(prova);
            }
        });

        return view;
    }
}
