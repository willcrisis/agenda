package willcrisis.com.agenda;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import willcrisis.com.agenda.converter.AlunoConverter;
import willcrisis.com.agenda.dao.AlunoRealmDAO;
import willcrisis.com.agenda.modelo.Aluno;

public class EnvioAlunosTask extends AsyncTask<Void, Void, String> {
    private final AlunoRealmDAO dao;
    private Context context;
    private ProgressDialog dialog;

    public EnvioAlunosTask(Context context) {
        this.context = context;
        this.dao = new AlunoRealmDAO(context);
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, "Aguarde", "Enviando alunos", true, true);
    }

    @Override
    protected String doInBackground(Void... voids) {
        AlunoConverter converter = new AlunoConverter();

        List<Aluno> alunos = dao.listar();

        String json = converter.converterLista(alunos);

        WebClient webClient = new WebClient();

        return webClient.post(json);
    }

    @Override
    protected void onPostExecute(String result) {
        dialog.dismiss();
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }
}
