package etsii.cm.amigoinvisible;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import adaptador.ListadoParticipantes_Adapter;
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
        getData();
    }

    public void getData(){
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
                            showData();
                        }
                    }
            );
            }
        });
        tr.start();
    }
    public void showData(){

        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        ImageView imgVwEventPhoto    = (ImageView)findViewById(R.id.imgVwEventPhoto);
        TextView  txtVwEventDate     = (TextView) findViewById(R.id.txtVwEventDate);
        TextView  txtVwEventTime     = (TextView) findViewById(R.id.txtVwEventTime);
        TextView  txtVwEventPlace    = (TextView) findViewById(R.id.txtVwEventPlace);
        TextView  txtVwEventMaxPrice = (TextView) findViewById(R.id.txtVwEventMaxPrice);

        imgVwEventPhoto.setImageBitmap(eventoActual.getData_photo());
        txtVwEventDate.setText(" " + eventoActual.getData_date_text());
        txtVwEventTime.setText(eventoActual.getData_date_time());
        txtVwEventPlace.setText(eventoActual.getData_place());
        txtVwEventMaxPrice.setText(eventoActual.getData_max_Price().toString() + " €");

        ListadoParticipantes_Adapter adapter = new ListadoParticipantes_Adapter(this, lstParticipants);
        listado.setAdapter(adapter);
    }

}
