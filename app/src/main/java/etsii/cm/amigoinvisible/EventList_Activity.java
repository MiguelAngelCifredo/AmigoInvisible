package etsii.cm.amigoinvisible;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

import adaptador.ListadoEventos_Adapter;
import dbms.RunInDB;
import model.ClsEvent;
import utils.Comunicador;
import utils.Contactos;
import utils.Iam;

public class EventList_Activity extends AppCompatActivity implements Serializable {
    private RunInDB db = new RunInDB();
    private ListView lstVwEvent;
    private ArrayList<ClsEvent> lstEvents;

    private static boolean add = false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.event_list, menu);
        findViewById(R.id.btnAddEvent).setVisibility(View.INVISIBLE);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.opcProfile) {
            Intent nextView = new Intent(getApplicationContext(), Profile_Activity.class);
            startActivity(nextView);
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        findViewById(R.id.btnAddEvent).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                add = true;
                Comunicador.setObjeto(new ClsEvent(0, "", "", "", 0, null, Iam.getId()));
                Intent nextView = new Intent(getApplicationContext(), EventEdit_Activity.class);
                startActivity(nextView);
            }
        });

        lstVwEvent = (ListView) findViewById(R.id.lstVwEvent);
        lstVwEvent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                Comunicador.setObjeto(lstEvents.get(i));
                Intent nextView = new Intent(getApplicationContext(), EventDetail_Activity.class);
                startActivity(nextView);
            }
        });

        getData(this);

        getContacts(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData(this);
    }

    private void getData(final Activity activity){
        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
                String eMail = Iam.gMailAccount(activity);
                Iam.setId( db.getPersonIdByEmail(eMail) );
                Iam.setEmail( eMail );
                //Iam.setId(1);
                //Iam.setEmail( "macifredo@gmail.com" );
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

    private void showData(){
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        findViewById(R.id.btnAddEvent).setVisibility(View.VISIBLE);
        ListadoEventos_Adapter adapter = new ListadoEventos_Adapter(this, lstEvents);
        lstVwEvent.setAdapter(adapter);
    }

    private void getContacts(final Activity activity){
        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
                Contactos.getContacts(activity);
            }
        });
        tr.start();
    }

}