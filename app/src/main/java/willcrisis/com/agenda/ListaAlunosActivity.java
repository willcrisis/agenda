package willcrisis.com.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import willcrisis.com.agenda.dao.AlunoDAO;
import willcrisis.com.agenda.modelo.Aluno;

public class ListaAlunosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        Button btnNovo = (Button) findViewById(R.id.lista_alunos_novo);
        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaAlunosActivity.this, NovoAlunoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void listarAlunos() {
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.listar();
        dao.close();

        ListView listaAlunos = (ListView) findViewById(R.id.lista_alunos);
        ArrayAdapter<Aluno> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alunos);
        listaAlunos.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        listarAlunos();
    }
}
