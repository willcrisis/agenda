package willcrisis.com.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import willcrisis.com.agenda.dao.AlunoDAO;
import willcrisis.com.agenda.modelo.Aluno;

public class ListaAlunosActivity extends AppCompatActivity {

    private ListView listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        listaAlunos = (ListView) findViewById(R.id.lista_alunos);

        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(position);

                Intent intent = new Intent(ListaAlunosActivity.this, NovoAlunoActivity.class);
                intent.putExtra("aluno", aluno);
                startActivity(intent);
            }
        });

        Button btnNovo = (Button) findViewById(R.id.lista_alunos_novo);
        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaAlunosActivity.this, NovoAlunoActivity.class);
                startActivity(intent);
            }
        });

        registerForContextMenu(listaAlunos);
    }

    private void listarAlunos() {
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.listar();
        dao.close();


        ArrayAdapter<Aluno> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alunos);
        listaAlunos.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        listarAlunos();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuItem excluir = menu.add("Excluir");
        excluir.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AdapterView.AdapterContextMenuInfo contextMenuInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(contextMenuInfo.position);

                AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                dao.excluir(aluno);
                dao.close();

                listarAlunos();

                Toast.makeText(ListaAlunosActivity.this, "Aluno " + aluno.getNome()  + " excluído", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}
