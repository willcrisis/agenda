package willcrisis.com.agenda;

import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import willcrisis.com.agenda.modelo.Aluno;

/**
 * Created by kraus on 17/08/2016.
 */
public class NovoAlunoHelper {
    private final EditText campoNome;
    private final EditText campoEndereco;
    private final EditText campoTelefone;
    private final EditText campoEmail;
    private final EditText campoSite;
    private final RatingBar campoNota;

    public NovoAlunoHelper(NovoAlunoActivity activity) {
        campoNome = (EditText) activity.findViewById(R.id.novo_aluno_nome);
        campoEndereco = (EditText) activity.findViewById(R.id.novo_aluno_endereco);
        campoTelefone = (EditText) activity.findViewById(R.id.novo_aluno_telefone);
        campoEmail = (EditText) activity.findViewById(R.id.novo_aluno_email);
        campoSite = (EditText) activity.findViewById(R.id.novo_aluno_site);
        campoNota = (RatingBar) activity.findViewById(R.id.novo_aluno_nota);
    }

    public Aluno getAluno() {
        Aluno aluno = new Aluno();
        aluno.setNome(campoNome.getText().toString());
        aluno.setEndereco(campoEndereco.getText().toString());
        aluno.setTelefone(campoTelefone.getText().toString());
        aluno.setEmail(campoEmail.getText().toString());
        aluno.setSite(campoSite.getText().toString());
        aluno.setNota(Double.valueOf(campoNota.getProgress()));

        return aluno;
    }
}
