package br.com.trainning.pdv.ui;

//Essa classe serve para utilizar ButterKnife nas Activities

import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;

/**
 * Created by elcio on 23/11/15.
 */
public class BaseActivity extends AppCompatActivity {


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }
}

