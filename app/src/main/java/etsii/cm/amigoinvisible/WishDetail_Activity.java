package etsii.cm.amigoinvisible;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.io.Serializable;

import dbms.RunInDB;
import model.ClsWish;
import utils.Comunicador;

public class WishDetail_Activity extends AppCompatActivity implements Serializable{

    private RunInDB db = new RunInDB();
    private ClsWish wishActual;
    private String bought;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        wishActual = (ClsWish) Comunicador.getObjeto();

        setContentView(R.layout.activity_wish_detail);

        ImageView imgVwWishPhoto       = (ImageView) findViewById(R.id.imgVwWishPhoto);
        TextView  txtVwWishDescription = (TextView)  findViewById(R.id.txtVwWishDescription);
        Switch    btnSwWishBought      = (Switch)    findViewById(R.id.btnSwWishBought);

        imgVwWishPhoto.setImageBitmap(wishActual.getData_photo());

        txtVwWishDescription.setText(wishActual.getData_description());

        btnSwWishBought.setChecked((wishActual.getData_bought().equals("Y")) ? true : false);

        btnSwWishBought.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                bought = (isChecked) ? "Y" : "N";
                grabarStatus();
                finish();
            }

        });
    }

    public void grabarStatus(){
        wishActual.setData_bought(bought);
        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
                db.setWishBought(wishActual.getData_id_wish(), bought);
            }
        });
        tr.start();
    }

}