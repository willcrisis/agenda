package willcrisis.com.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import willcrisis.com.agenda.modelo.Aluno;

/**
 * Created by kraus on 17/08/2016.
 */
public class AlunoDAO extends SQLiteOpenHelper {
    public AlunoDAO(Context context) {
        super(context, "Agenda", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE Aluno (id INTEGER PRIMARY KEY, nome TEXT NOT NULL, telefone TEXT, endereco TEXT, email TEXT, site TEXT, nota REAL);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String sql = "DROP TABLE  IF EXISTS Alunos;";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public void incluir(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("telefone", aluno.getTelefone());
        values.put("endereco", aluno.getEndereco());
        values.put("email", aluno.getEmail());
        values.put("site", aluno.getSite());
        values.put("nota", aluno.getNota());

        db.insert("Aluno", null, values);
    }

    public List<Aluno> listar() {
        String sql = "SELECT * from Aluno;";

        List<Aluno> alunos = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        while (c.moveToNext()) {
            Aluno aluno = new Aluno();
            aluno.setId(c.getLong(c.getColumnIndex("id")));
            aluno.setNome(c.getString(c.getColumnIndex("nome")));
            aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
            aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
            aluno.setEmail(c.getString(c.getColumnIndex("email")));
            aluno.setSite(c.getString(c.getColumnIndex("site")));
            aluno.setNota(c.getDouble(c.getColumnIndex("nota")));

            alunos.add(aluno);
        }
        c.close();

        return alunos;
    }
}
