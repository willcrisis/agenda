package willcrisis.com.agenda;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import willcrisis.com.agenda.dao.AlunoDAO;
import willcrisis.com.agenda.modelo.Aluno;

public class NovoAlunoActivity extends AppCompatActivity {

    public static final int CODIGO_CAMERA = 1;
    private NovoAlunoHelper helper;
    private String caminhoArquivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_aluno);

        Intent intent = getIntent();
        Aluno aluno = (Aluno) intent.getSerializableExtra("aluno");

        helper = new NovoAlunoHelper(this);

        if (aluno != null) {
            helper.popularCampos(aluno);
        }

        Button btnCamera = (Button) findViewById(R.id.novo_aluno_camera);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                caminhoArquivo = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
                File caminho = new File(caminhoArquivo);
                camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(caminho));
                startActivityForResult(camera, CODIGO_CAMERA);
            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CODIGO_CAMERA) {
                helper.carregarImagem(caminhoArquivo);
            }
        }
    }
}
