package willcrisis.com.agenda.converter;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

import willcrisis.com.agenda.modelo.Aluno;

public class AlunoConverter {

    public String converterLista(List<Aluno> alunos) {
        JSONStringer js = new JSONStringer();

        try {
            js.object().key("list").array().object().key("aluno").array();
            for (Aluno aluno : alunos) {
                js.object();
                js.key("nome").value(aluno.getNome());
                js.key("nota").value(aluno.getNota());
                js.endObject();
            }
            js.endArray().endObject().endArray().endObject();
            System.out.println(js.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return js.toString();
    }
}
