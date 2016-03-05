package br.com.trainning.pdv.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

import br.com.trainning.pdv.R;
import br.com.trainning.pdv.domain.model.Produto;
import br.com.trainning.pdv.domain.util.Base64Util;
import br.com.trainning.pdv.domain.util.ImageInputHelper;
import butterknife.Bind;
import butterknife.OnClick;

//Trocamos extends AppCompatActivity por BaseAcitivity para usar o ButterKnife. A classe BaseActivity extende AppCompatActivity
public class CadastroNovoActivity extends BaseActivity implements ImageInputHelper.ImageActionListener{

    //Declaracao para usar ButterKnife. Faz a função do FindViewbyId. Fica só na declaração. Não precisa colocar o findViewById no OnCreate
    @Bind(R.id.editTextDescricao)
    EditText editTextDescricao;
    @Bind(R.id.editTextUnidade)
    EditText editTextUnidade;
    @Bind(R.id.editTextPreco)
    EditText editTextPreco;
    @Bind(R.id.editTextCodigo)
    EditText editTextCodigo;

    @Bind(R.id.imageViewFoto)
    ImageView imageViewFoto;
    @Bind(R.id.imageButtonCamera)
    ImageButton imageButtonCamera;
    @Bind(R.id.imageButtonGaleria)
    ImageButton imageButtonGaleria;

    private ImageInputHelper imageInputHelper;
    private Produto produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_novo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageInputHelper = new ImageInputHelper(this);
        imageInputHelper.setImageActionListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Cadastro",editTextDescricao.getText().toString());
                Log.d("Cadastro",editTextUnidade.getText().toString());
                Log.d("Cadastro",editTextPreco.getText().toString());
                Log.d("Cadastro",editTextCodigo.getText().toString());
                //Cria classe produto para inserir dados no banco usando Sprinkles
                produto = new Produto();
                produto.setId(0L); //Especifica que é um 0 tipo long para criar um novo. Se for diferente de 0 e existir na tabela, ele atualiza o existente
                produto.setDescricao(editTextDescricao.getText().toString());
                produto.setUnidade(editTextUnidade.getText().toString());
                produto.setCodigoBarras(editTextCodigo.getText().toString());
                //Checa se existe preço para converter de String para Double
                if(!editTextPreco.getText().toString().equals("")){
                    produto.setPreco(Double.parseDouble(editTextPreco.getText().toString()));
                }else{
                    produto.setPreco(0.0);
                }

                //Pega a foto do imageview e converte em Bitmap
                Bitmap imagem = ((BitmapDrawable)imageViewFoto.getDrawable()).getBitmap();
                //Converte de Bitmap para String base 64 e armazena no campo foto do BD
                produto.setFoto(Base64Util.encodeTobase64(imagem));
                //Salva na tabela do banco de dados.
                produto.save();
                //Finaliza a Activity
                finish();

            }
        });
    }

    //Método Click do botao galeria. Implmentação usando Butter Knife
    @OnClick(R.id.imageButtonGaleria)
    public void onClickGaleria(){
        imageInputHelper.selectImageFromGallery();
    }

    @OnClick(R.id.imageButtonCamera)
    public void onClickCamera(){
        imageInputHelper.takePhotoWithCamera();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageInputHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onImageSelectedFromGallery(Uri uri, File imageFile) {
        // cropping the selected image. crop intent will have aspect ratio 16/9 and result image
        // will have size 800x450
        imageInputHelper.requestCropImage(uri, 100, 100, 0, 0);
    }

    @Override
    public void onImageTakenFromCamera(Uri uri, File imageFile) {
        // cropping the taken photo. crop intent will have aspect ratio 16/9 and result image
        // will have size 800x450
        imageInputHelper.requestCropImage(uri, 100, 100, 0, 0);
    }

    @Override
    public void onImageCropped(Uri uri, File imageFile) {
        try {
            // getting bitmap from uri
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

            imageViewFoto.setImageBitmap(bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
