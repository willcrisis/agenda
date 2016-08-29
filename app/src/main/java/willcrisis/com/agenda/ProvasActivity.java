package willcrisis.com.agenda;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import willcrisis.com.agenda.modelo.Prova;

public class ProvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        tx.replace(R.id.provas_lista, new ListaProvasFragment());
        if (isPaisagem()) {
            tx.replace(R.id.provas_detalhe, new DetalhesProvaFragment());
        }
        tx.commit();
    }

    private boolean isPaisagem() {
        return getResources().getBoolean(R.bool.paisagem);
    }

    public void selecionarProva(Prova prova) {
        FragmentManager fm = getSupportFragmentManager();
        if (isPaisagem()) {
            DetalhesProvaFragment detalhes = (DetalhesProvaFragment) fm.findFragmentById(R.id.provas_detalhe);
            detalhes.popularCampos(prova);
        } else {
            Bundle args = new Bundle();
            args.putSerializable("prova", prova);
            DetalhesProvaFragment fragment = new DetalhesProvaFragment();
            fragment.setArguments(args);

            FragmentTransaction tx = fm.beginTransaction();
            tx.replace(R.id.provas_lista, fragment);
            tx.addToBackStack(null);
            tx.commit();
        }
    }
}
