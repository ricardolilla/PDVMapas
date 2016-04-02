package br.com.trainning.pdv.ui;
import android.os.Bundle;

import android.util.Log;

import com.mapbox.mapboxsdk.annotations.MarkerOptions;

import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.views.MapView;


import java.util.List;

import br.com.trainning.pdv.R;
import br.com.trainning.pdv.domain.model.Produto;
import br.com.trainning.pdv.domain.util.Util;
import butterknife.Bind;
import se.emilsjolander.sprinkles.CursorList;
import se.emilsjolander.sprinkles.Query;

public class MapaActivity extends BaseActivity {

    @Bind(R.id.mapview)
    MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);


        mapView.setStyleUrl(Style.MAPBOX_STREETS);
        mapView.setCenterCoordinate(new LatLng(-23.5586729,-46.6612236));
        mapView.setZoomLevel(12);

        CursorList cursorList = Query.all(Produto.class).get();

        List<Produto> listaProdutos = cursorList.asList();


        //Colocar marcadores no mapa onde foram cadastrados os produtos
        for(Produto produto : listaProdutos) {
            if(produto.getLatitude()+produto.getLongitude() != 0.0){
                Log.d("PRODUTO", produto.getLatitude() + " " + produto.getLongitude());
                mapView.addMarker(new MarkerOptions()
                        .position(new LatLng(produto.getLatitude(), produto.getLongitude()))
                        .title(produto.getDescricao())
                        .snippet(Util.getCurrencyValue(produto.getPreco()) + " " + produto.getUnidade()));
            }
        }

        mapView.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause()  {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}