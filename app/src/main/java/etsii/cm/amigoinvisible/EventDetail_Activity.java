package etsii.cm.amigoinvisible;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import dbms.RunInDB;
import model.ClsEvent;
import model.ClsParticipant;

public class EventDetail_Activity extends AppCompatActivity implements Serializable {

    private RunInDB db = new RunInDB();
    private ListView listado;
    private ClsEvent eventoActual;
    private ArrayList<ClsParticipant> lstParticipants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        listado = (ListView) findViewById(R.id.lstVwParticipants);
        //Cuando vengamos a esta Activity, ya traemos el Objeto event
        //por lo que la línea que viene ahora se des-comenta.
        //eventoActual = (ClsEvent) Comunicador.getObjeto();
        obtieneDatosEvento();
    }

    public void obtieneDatosEvento (){
        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {

                //Cuando vengamos a esta Activity, ya traemos el Objeto event
                //por lo que las DOS líneas siguientes sobran (se quitan)
                ArrayList<ClsEvent> listaEventos = db.getListEvents();
                eventoActual = listaEventos.get(0);

                lstParticipants = db.getListParticipants(eventoActual.getData_id_event());

                runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                mostrarListado();
                            }
                        }
                );
            }
        });
        tr.start();
    }
    public void mostrarListado(){

        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        ImageView imgEventPhoto = (ImageView)findViewById(R.id.imgVwEventPhoto);
        imgEventPhoto.setImageBitmap(eventoActual.getData_photo());

        TextView txtEventDate = (TextView) findViewById(R.id.txtVwEventDate);
        txtEventDate.setText(" " + eventoActual.getData_date_text());

        TextView txtEventTime = (TextView) findViewById(R.id.txtVwEventTime);
        txtEventTime.setText(eventoActual.getData_date_time());

        TextView txtEventPlace = (TextView) findViewById(R.id.txtVwEventPlace);
        txtEventPlace.setText(eventoActual.getData_place());

        TextView txtEventMaxPrice = (TextView) findViewById(R.id.txtVwEventMaxPrice);
        txtEventMaxPrice.setText(eventoActual.getData_max_Price().toString() + " €");

        Adaptador_lista_participantes adapter = new Adaptador_lista_participantes(this, lstParticipants);
        listado.setAdapter(adapter);
    }

}
