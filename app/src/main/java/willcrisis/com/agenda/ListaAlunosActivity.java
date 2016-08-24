package willcrisis.com.agenda;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import willcrisis.com.agenda.adapter.AlunoAdapter;
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

        if (ActivityCompat.checkSelfPermission(ListaAlunosActivity.this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ListaAlunosActivity.this, new String[]{Manifest.permission.RECEIVE_SMS}, 1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_alunos, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_lista_alunos_enviar:
                EnvioAlunosTask task = new EnvioAlunosTask(this);
                task.execute();
                break;
        }
        return true;
    }

    private void listarAlunos() {
        List<Aluno> alunos = getAlunos();


        AlunoAdapter adapter = new AlunoAdapter(this, alunos);
        listaAlunos.setAdapter(adapter);
    }

    private List<Aluno> getAlunos() {
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.listar();
        dao.close();
        return alunos;
    }

    @Override
    protected void onResume() {
        super.onResume();
        listarAlunos();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo contextMenuInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(contextMenuInfo.position);

        MenuItem ligar = menu.add("Ligar para aluno");
        ligar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (ActivityCompat.checkSelfPermission(ListaAlunosActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ListaAlunosActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + aluno.getTelefone()));
                    startActivity(intent);
                }

                return false;
            }
        });

        MenuItem mandarSms = menu.add("Mandar SMS");
        Intent intentSms = new Intent(Intent.ACTION_VIEW);
        intentSms.setData(Uri.parse("sms:" + aluno.getTelefone()));
        mandarSms.setIntent(intentSms);

        MenuItem acharNoMapa = menu.add("Achar no mapa");
        Intent intentGeo = new Intent(Intent.ACTION_VIEW);
        intentGeo.setData(Uri.parse("geo:0,0?q=" + aluno.getEndereco()));
        acharNoMapa.setIntent(intentGeo);

        MenuItem visitarSite = menu.add("Visitar site");
        String site = aluno.getSite();
        if (!(site.startsWith("http://") || site.startsWith("https://"))) {
            site = "http://" + site;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(site));

        visitarSite.setIntent(intent);

        MenuItem excluir = menu.add("Excluir");
        excluir.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
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
