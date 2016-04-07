package etsii.cm.amigoinvisible;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;

import dbms.getInfo;
import model.ClsEvent;

/**
 * Created by criperrui on 07/04/2016.
 */
public class ViewEventActivity extends AppCompatActivity implements Serializable {

    private TextView txtVwEventName = null;
    private TextView txtVwFriendName = null;
    private ImageButton imgbtnVwInfo = null;
    private ImageView imgVwEvent = null;
    private ListView listItemView = null;

    public getInfo db = new getInfo();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);

        Intent recepcion = getIntent();


        Integer id = recepcion.getIntExtra("idEvent", -1);
        String name = recepcion.getStringExtra("nameEvent");
        String date = recepcion.getStringExtra("dateEvent");
        String place = recepcion.getStringExtra("placeEvent");
        Integer price = recepcion.getIntExtra("priceEvent", -1);


        ClsEvent miEventoRecibido = new ClsEvent(id,name,date,place,price);

        TextView txtCambiado = (TextView)findViewById(R.id.txtVwEventName);
        txtCambiado.setText(name);


        //System.out.println("Aquí tengo  mi evento desde la otra activity " + miEventoRecibido);

    }

}
