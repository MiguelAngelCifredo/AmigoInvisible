package etsii.cm.amigoinvisible;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

import adaptador.ListadoEventos_Adapter;
import dbms.RunInDB;
import model.ClsEvent;
import model.ClsPerson;
import utils.Comunicador;
import utils.Iam;

public class EventList_Activity extends AppCompatActivity implements Serializable {
    private RunInDB db = new RunInDB();
    private ListView listado;
    private ArrayList<ClsEvent> lstEvents;
    private ClsPerson personActual;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        listado = (ListView) findViewById(R.id.lstVwEvent);
        listado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                Intent nextView = new Intent(getApplicationContext(), MyFriend_Activity.class);
                Comunicador.setObjeto(lstEvents.get(i));
                startActivity(nextView);
            }
        });

        getData(this);
    }

    private void getData (final Activity activity){
        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
                String eMail = Iam.gMailAccount(activity);
                Iam.setId( db.getAccount(eMail) );
                //Iam.setId(16);
                Iam.setEmail( eMail );
                lstEvents = db.getListEvents();
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

        ListadoEventos_Adapter adapter = new ListadoEventos_Adapter(this, lstEvents);
        listado.setAdapter(adapter);
    }

}