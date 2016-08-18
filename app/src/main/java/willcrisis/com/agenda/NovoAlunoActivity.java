package willcrisis.com.agenda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import willcrisis.com.agenda.dao.AlunoDAO;
import willcrisis.com.agenda.modelo.Aluno;

public class NovoAlunoActivity extends AppCompatActivity {

    private NovoAlunoHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_aluno);

        helper = new NovoAlunoHelper(this);
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
                dao.incluir(aluno);
                dao.close();

                Toast.makeText(NovoAlunoActivity.this, "Aluno " + aluno.getNome() + " salvo", Toast.LENGTH_LONG).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
