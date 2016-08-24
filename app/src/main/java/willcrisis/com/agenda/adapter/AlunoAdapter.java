package willcrisis.com.agenda.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import willcrisis.com.agenda.R;
import willcrisis.com.agenda.modelo.Aluno;

/**
 * Created by kraus on 23/08/2016.
 */
public class AlunoAdapter extends BaseAdapter {
    private final Context context;
    private final List<Aluno> alunos;

    public AlunoAdapter(Context context, List<Aluno> alunos) {
        this.context = context;
        this.alunos = alunos;
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int i) {
        return alunos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return ((Aluno) getItem(i)).getId();
    }

    @Override
    public View getView(int i, View viewHolder, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.list_item, parent, false);
        }

        Aluno aluno = (Aluno) getItem(i);

        TextView campoNome = (TextView) view.findViewById(R.id.item_nome);
        campoNome.setText(aluno.getNome());

        TextView campoTelefone = (TextView) view.findViewById(R.id.item_telefone);
        campoTelefone.setText(aluno.getTelefone());

        ImageView campoFoto = (ImageView) view.findViewById(R.id.item_foto);
        if (aluno.getCaminhoFoto() != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(aluno.getCaminhoFoto());
            bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            campoFoto.setImageBitmap(bitmap);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        return view;
    }
}
