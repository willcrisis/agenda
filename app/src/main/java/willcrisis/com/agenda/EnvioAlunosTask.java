package willcrisis.com.agenda;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import willcrisis.com.agenda.converter.AlunoConverter;
import willcrisis.com.agenda.dao.AlunoDAO;
import willcrisis.com.agenda.modelo.Aluno;

/**
 * Created by kraus on 24/08/2016.
 */
public class EnvioAlunosTask extends AsyncTask<Void, Void, String> {
    private Context context;
    private ProgressDialog dialog;

    public EnvioAlunosTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, "Aguarde", "Enviando alunos", true, true);
    }

    @Override
    protected String doInBackground(Void... voids) {
        AlunoConverter converter = new AlunoConverter();

        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.listar();
        dao.close();

        String json = converter.converterLista(alunos);

        WebClient webClient = new WebClient();
        String result = webClient.post(json);

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        dialog.dismiss();
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }
}
