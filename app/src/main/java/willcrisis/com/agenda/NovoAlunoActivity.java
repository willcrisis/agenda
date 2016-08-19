package willcrisis.com.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import willcrisis.com.agenda.dao.AlunoDAO;
import willcrisis.com.agenda.modelo.Aluno;

public class NovoAlunoActivity extends AppCompatActivity {

    private NovoAlunoHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_aluno);

        Intent intent = getIntent();
        Aluno aluno = (Aluno) intent.getSerializableExtra("aluno");

        helper = new NovoAlunoHelper(this);

        if (aluno != null) {
            helper.popularAluno(aluno);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_novo_aluno, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_novo_aluno_salvar:
                Aluno aluno = helper.getAluno();

                AlunoDAO dao = new AlunoDAO(this);
                if (aluno.getId() == null) {
                    dao.incluir(aluno);
                } else {
                    dao.alterar(aluno);
                }

                dao.close();

                Toast.makeText(NovoAlunoActivity.this, "Aluno " + aluno.getNome() + " salvo", Toast.LENGTH_LONG).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
