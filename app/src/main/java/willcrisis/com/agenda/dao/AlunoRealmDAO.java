package willcrisis.com.agenda.dao;

import android.content.Context;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import willcrisis.com.agenda.modelo.Aluno;

public class AlunoRealmDAO {

    private final Realm realm;

    public AlunoRealmDAO(Context context) {
        RealmConfiguration.Builder builder = new RealmConfiguration.Builder(context);
        Realm.setDefaultConfiguration(builder.build());
        realm = Realm.getDefaultInstance();
    }

    public void incluir(final Aluno aluno) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(aluno);
            }
        });
    }

    public void alterar(final Aluno aluno) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(aluno);
            }
        });
    }

    public void excluir(final Aluno aluno) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Aluno.class).equalTo("id", aluno.getId()).findFirst().deleteFromRealm();
            }
        });
    }

    public Aluno obter(Long id) {
        return realm.copyFromRealm(getQuery().equalTo("id", id).findFirst());
    }

    public List<Aluno> listar() {
        return realm.where(Aluno.class).findAllSorted("nome");
    }

    public RealmQuery<Aluno> getQuery() {
        return realm.where(Aluno.class);
    }

    public Long proximoId() {
        try {
            return getQuery().max("id").longValue() + 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 1L;
        }
    }
}
