package etsii.cm.amigoinvisible;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

import dbms.getInfo;
import model.ClsWish;

public class WishEdit_Activity extends AppCompatActivity implements Serializable{
    private getInfo db = new getInfo();
    private ClsWish deseoActual;
    private ImageView imgWishPhoto;
    private TextView txtWishText;
    private TextView txtWishDescription;
    private Button btnWishEditSave;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_edit);

        imgWishPhoto = (ImageView) findViewById(R.id.imgVwWishEditPhoto);
        txtWishText = (TextView) findViewById(R.id.txtVwWishEditText);
        txtWishDescription = (TextView) findViewById(R.id.txtVwWishEditDescription);

        btnWishEditSave = (Button) findViewById(R.id.btnWishEditSave);
        btnWishEditSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                grabarWish();
            }
        });
        //deseoActual = (ClsWish) Comunicador.getObjeto();
        leerWish();
    }

    public void leerWish(){
        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
                deseoActual = db.getWish(9);
                runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                mostrarDatos();
                            }
                        }
                );
            }
        });
        tr.start();
    }

    public void grabarWish(){
        deseoActual.setData_text(txtWishText.getText().toString());
        deseoActual.setData_description(txtWishDescription.getText().toString());
        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
                db.setWish(deseoActual);
            }
        });
        tr.start();
    }

    public void mostrarDatos(){
        imgWishPhoto.setImageBitmap(deseoActual.getData_photo());
        txtWishText.setText(deseoActual.getData_text());
        txtWishDescription.setText(deseoActual.getData_description());
    }
}
