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
    private ClsWish actualWish;
    private String bought;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actualWish = (ClsWish) Comunicador.getObjeto();

        setTitle(actualWish.getData_text());
        setContentView(R.layout.activity_wish_detail);

        ImageView imgVwWishPhoto       = (ImageView) findViewById(R.id.imgVwWishPhoto);
        TextView  txtVwWishDescription = (TextView)  findViewById(R.id.txtVwWishDescription);
        Switch    btnSwWishBought      = (Switch)    findViewById(R.id.btnSwWishBought);


        if (actualWish.getData_photo() != null){
            imgVwWishPhoto.setImageBitmap(actualWish.getData_photo());
        }

        txtVwWishDescription.setText(actualWish.getData_description());

        btnSwWishBought.setChecked((actualWish.getData_bought().equals("Y")) ? true : false);

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
        actualWish.setData_bought(bought);
        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
                db.setWishBought(actualWish.getData_id_wish(), bought);
            }
        });
        tr.start();
    }

}