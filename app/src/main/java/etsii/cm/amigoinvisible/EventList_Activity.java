package etsii.cm.amigoinvisible;

import android.Manifest;
import android.accounts.Account;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

import adaptador.ListadoEventos_Adapter;
import dbms.RunInDB;
import model.ClsEvent;
import model.ClsParticipant;
import utils.Comunicador;
import utils.Empareja;
import utils.I_am;

public class EventList_Activity extends AppCompatActivity implements Serializable {
    private RunInDB db = new RunInDB();
    private ArrayList<ClsEvent> lstEvents;
    private ListView listado;
    private ArrayList<ClsParticipant> lsParticipants;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        //obtieneListaParticipantes(); //finish();
        System.out.println("****** MI EMAIL ES: " + I_am.getGmailAccount(this));

        listado = (ListView) findViewById(R.id.lstVwEvent);
        listado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                Intent miIntent = new Intent(getApplicationContext(), MyFriend_Activity.class);
                Comunicador.setObjeto(lstEvents.get(i));
                startActivity(miIntent);
               }
        });
        obtieneListaEventos();
    }

     public void obtieneListaEventos (){
        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
                lstEvents = db.getListEvents();
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
        ListadoEventos_Adapter adapter = new ListadoEventos_Adapter(this, lstEvents);
        listado.setAdapter(adapter);
    }

    // provisional
    public void obtieneListaParticipantes (){
        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
                lsParticipants = db.getListParticipants(1);
                runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                test();
                            }
                        }
                );
            }
        });
        tr.start();
    }
    private void test(){
        System.out.println("@@@###@@@ : " + lsParticipants);

        for(ClsParticipant part: lsParticipants){
            System.out.println("@@@###@@@ INICIO: " + part.getData_person().getData_id_person() + " <-> " + part.getData_friend().getData_id_person());
        }
        Empareja.mezclaParticipantes(lsParticipants, 10);
        for(ClsParticipant part: lsParticipants){
            System.out.println("@@@###@@@ BARAJADO: " + part.getData_person().getData_id_person() + " <-> " + part.getData_friend().getData_id_person());
        }
    }


}