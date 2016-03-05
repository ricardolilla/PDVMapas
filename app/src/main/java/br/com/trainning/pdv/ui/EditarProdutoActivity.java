package br.com.trainning.pdv.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import br.com.trainning.pdv.R;
import br.com.trainning.pdv.domain.model.Produto;
import butterknife.Bind;
import se.emilsjolander.sprinkles.Query;

//Extendemos BaseActivity para usar Butter Knife
public class EditarProdutoActivity extends BaseActivity {

    @Bind(R.id.spinner)
    Spinner spinner;
    private Produto produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_produto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Pegar lista de produtos, ordenar por Codigo barras e popular spinner
        //No lugar de Query.all usamos Query.many para poder digitar o codigo sql
        //Exemplo se tiver Where List<Produto> produtos = Query.many(Produto.class, "select * from produto where id = ? order by codigo_barra",1).get().asList();
        List<Produto> produtos = Query.many(Produto.class, "select * from produto order by codigo_barra").get().asList();

        produto = new Produto();

        //Cria lista de todos os códigos de barras
        List<String> barcodeList = new ArrayList<>();

        //Adiciona todos os códigos de barras na lista barcodeList
        for(Produto produto: produtos){
            barcodeList.add(produto.getCodigoBarras());
        }

        //Sempre que usar View que seja lista tem que usar um ArrayAdapter. Çayout simple_spinner_item já fornecido pelo Android
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,barcodeList);
        //Tipo de spinner
        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        //Indica qual adapter usar
        spinner.setAdapter(dataAdapter);

        //Método chamado ao selecionar algo na lista
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String barcode = parent.getItemAtPosition(position).toString();

                Log.d("BARCODE", "Selecionado -->"+barcode);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

}
