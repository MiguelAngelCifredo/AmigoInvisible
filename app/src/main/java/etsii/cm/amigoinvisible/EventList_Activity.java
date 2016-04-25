package etsii.cm.amigoinvisible;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

import adaptador.ListadoEventos_Adapter;
import dbms.RunInDB;
import model.ClsEvent;
import model.ClsPerson;
import model.ClsWish;
import utils.Comunicador;
import utils.Iam;

public class EventList_Activity extends AppCompatActivity implements Serializable {
    private RunInDB db = new RunInDB();
    private ListView listado;
    private FloatingActionButton btnCrearEvento;
    private ArrayList<ClsEvent> lstEvents;
    private ClsPerson personActual;

    private static boolean add = false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_profile) {
            Intent nextView = new Intent(getApplicationContext(), Profile_Activity.class);
            startActivity(nextView);
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        btnCrearEvento = (FloatingActionButton) findViewById(R.id.btnCrearEvento);
        btnCrearEvento.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                add = true;
                Comunicador.setObjeto(new ClsEvent(0,"","","",0,null,Iam.getId()));
                Intent nextView = new Intent(getApplicationContext(), EventEdit_Activity.class);
                startActivity(nextView);
            }
        });

        listado = (ListView) findViewById(R.id.lstVwEvent);
        listado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                Comunicador.setObjeto(lstEvents.get(i));
                Intent nextView;
                if (lstEvents.get(i).getDataId_admin() == Iam.getId()) {
                    nextView = new Intent(getApplicationContext(), EventEdit_Activity.class);
                } else {
                    nextView = new Intent(getApplicationContext(), EventDetail_Activity.class);
                }
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