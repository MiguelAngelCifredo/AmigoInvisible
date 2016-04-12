package etsii.cm.amigoinvisible;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.io.Serializable;

import dbms.getInfo;
import model.ClsWish;

public class ViewWishActivity extends AppCompatActivity implements Serializable{

    private getInfo db = new getInfo();
    private ClsWish deseoActual;
    private String bought;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_detail);

        deseoActual = (ClsWish) Comunicador.getObjeto();

        //TextView txtWishText = (TextView)findViewById(R.id.txtWishText);
        //txtWishText.setText(deseoActual.getData_text());

        ImageView imgWishPhoto = (ImageView) findViewById(R.id.imgVwWishPhoto);
        imgWishPhoto.setImageBitmap(deseoActual.getData_photo());

        TextView txtWishDescription = (TextView) findViewById(R.id.txtVwWishDescription);
        txtWishDescription.setText(deseoActual.getData_description());

        Switch btnSwComprado = (Switch) findViewById(R.id.btnSwComprado);
        btnSwComprado.setChecked((deseoActual.getData_bouth().equals("Y")) ? true : false);
        btnSwComprado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                bought = (isChecked) ? "Y" : "N";
                grabarStatus();
            }

        });
    }

    public void grabarStatus(){
        deseoActual.setData_bouth(bought);
        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
                db.setWishBought(deseoActual.getData_id_wish(), bought);
            }
        });
        tr.start();
    }

}