package willcrisis.com.agenda;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
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
    private final ImageView campoFoto;
    private Aluno aluno;

    public NovoAlunoHelper(NovoAlunoActivity activity) {
        campoNome = (EditText) activity.findViewById(R.id.novo_aluno_nome);
        campoEndereco = (EditText) activity.findViewById(R.id.novo_aluno_endereco);
        campoTelefone = (EditText) activity.findViewById(R.id.novo_aluno_telefone);
        campoEmail = (EditText) activity.findViewById(R.id.novo_aluno_email);
        campoSite = (EditText) activity.findViewById(R.id.novo_aluno_site);
        campoNota = (RatingBar) activity.findViewById(R.id.novo_aluno_nota);
        campoFoto = (ImageView) activity.findViewById(R.id.novo_aluno_imagem);
        aluno = new Aluno();
    }

    public Aluno getAluno() {
        aluno.setNome(campoNome.getText().toString());
        aluno.setEndereco(campoEndereco.getText().toString());
        aluno.setTelefone(campoTelefone.getText().toString());
        aluno.setEmail(campoEmail.getText().toString());
        aluno.setSite(campoSite.getText().toString());
        aluno.setNota((double) campoNota.getProgress());
        aluno.setCaminhoFoto((String) campoFoto.getTag());

        return aluno;
    }

    public void popularCampos(Aluno aluno) {
        campoNome.setText(aluno.getNome());
        campoTelefone.setText(aluno.getTelefone());
        campoEndereco.setText(aluno.getEndereco());
        campoEmail.setText(aluno.getEmail());
        campoSite.setText(aluno.getSite());
        campoNota.setProgress(aluno.getNota().intValue());
        carregarImagem(aluno.getCaminhoFoto());
        this.aluno = aluno;
    }

    public void carregarImagem(String caminhoArquivo) {
        if (caminhoArquivo == null) {
            return;
        }

        Bitmap bitmap = BitmapFactory.decodeFile(caminhoArquivo);
        bitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
        campoFoto.setImageBitmap(bitmap);
        campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
        campoFoto.setTag(caminhoArquivo);
    }
}
