package willcrisis.com.agenda;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import willcrisis.com.agenda.dao.AlunoDAO;
import willcrisis.com.agenda.modelo.Aluno;

/**
 * Created by willian.krause on 29/08/2016.
 */
public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback {
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng posicao = obterCoordenadasDoEndereco("Av. Bela Vista, 11 - Jardim das Esmeraldas, Aparecida de Goiânia - GO");
        if (posicao != null) {
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(posicao, 17);
            googleMap.moveCamera(update);
        }

        AlunoDAO dao = new AlunoDAO(getContext());
        List<Aluno> lista = dao.listar();
        dao.close();

        for (Aluno aluno : lista) {
            LatLng coordenada = obterCoordenadasDoEndereco(aluno.getEndereco());
            if (coordenada != null) {
                MarkerOptions marcador = new MarkerOptions();
                marcador.position(coordenada);
                marcador.title(aluno.getNome());
                marcador.snippet(aluno.getNota().toString());

                googleMap.addMarker(marcador);
            }
        }

        new Localizador(getContext(), googleMap);
    }

    private LatLng obterCoordenadasDoEndereco(String endereco) {
        try {
            Geocoder geocoder = new Geocoder(getContext());
            List<Address> resultados = geocoder.getFromLocationName(endereco, 1);
            if (!resultados.isEmpty()) {

                Address address = resultados.get(0);
                return new LatLng(address.getLatitude(), address.getLongitude());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
