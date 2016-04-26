package etsii.cm.amigoinvisible;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
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
import utils.Comunicador;
import utils.Iam;

public class EventDetail_Activity extends AppCompatActivity implements Serializable {

    private RunInDB db = new RunInDB();
    private ClsEvent eventActual;
    private ArrayList<ClsParticipant> lstParticipants;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.event_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.friend) {
            Comunicador.setObjeto(eventActual);
            Intent nextView = new Intent(getApplicationContext(), MyFriend_Activity.class);
            startActivity(nextView);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventActual = (ClsEvent) Comunicador.getObjeto();
        setTitle(eventActual.getData_name());
        setContentView(R.layout.activity_event_detail);
        //getData();
        showData();
    }

    /*public void getData(){
        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
            lstParticipants = db.getListParticipants(eventActual.getData_id_event());
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
    }*/

    public void showData(){
        //findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        ImageView imgVwEventPhoto    = (ImageView)findViewById(R.id.imgVwEventPhoto);
        TextView  txtVwEventDate     = (TextView) findViewById(R.id.txtVwEventDate);
        TextView  txtVwEventTime     = (TextView) findViewById(R.id.txtVwEventTime);
        TextView  txtVwEventPlace    = (TextView) findViewById(R.id.txtVwEventPlace);
        TextView  txtVwEventMaxPrice = (TextView) findViewById(R.id.txtVwEventMaxPrice);

        if (eventActual.getData_photo() != null) {
            imgVwEventPhoto.setImageBitmap(eventActual.getData_photo());
        }

        txtVwEventDate.setText(" " + eventActual.getData_date_text());
        txtVwEventTime.setText(eventActual.getData_date_time());
        txtVwEventPlace.setText(eventActual.getData_place());
        txtVwEventMaxPrice.setText(eventActual.getData_max_Price().toString() + " €");

        /*ListadoParticipantes_Adapter adapter = new ListadoParticipantes_Adapter(this, lstParticipants);
        ListView listado = (ListView) findViewById(R.id.lstVwParticipants);
        listado.setAdapter(adapter);*/
    }

}