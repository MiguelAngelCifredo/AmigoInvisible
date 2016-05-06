package etsii.cm.amigoinvisible;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import adaptador.ListadoEventos_Adapter;
import dbms.RunInDB;
import model.ClsEvent;
import model.ClsParticipant;
import model.ClsPerson;
import utils.Comunicador;
import utils.Contactos;
import utils.Iam;

public class EventList_Activity extends AppCompatActivity implements Serializable {
    private RunInDB db = new RunInDB();
    private ListView lstVwEvent;
    private ArrayList<ClsEvent> lstEvents;
    private Activity actualActivity;

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
        actualActivity = this;

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
        lstVwEvent.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView adapterView, View view, int i, long l) {
                if (Iam.admin(lstEvents.get(i))) {
                    Comunicador.setObjeto(lstEvents.get(i));
                    dialogConfirmDeleteEvent();
                }
                return true;
            }
        });

        getContacts(actualActivity);
        getIam(actualActivity);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (add){
            add = false;
            ClsEvent newEvent = (ClsEvent) Comunicador.getObjeto();
            if (!newEvent.getData_date().equals("")) {
                lstEvents.add(newEvent);
            }
            showData();
        } else {
            getData(actualActivity);
        }
    }

    private void getIam(final Activity activity){
        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
                String eMail = Iam.gMailAccount(activity);
                Iam.setId( db.getPersonIdByEmail(eMail) );
                Iam.setEmail( eMail );
                // Si el propietario del dispositivo no existe en la BD, se añade.
                if ( Iam.getId()==0 ){
                    Iam.setName("Pon tu nombre.");
                    Iam.setPhoto( Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888));
                    db.insPerson(new ClsPerson(0, Iam.getEmail(), Iam.getName(), Iam.getPhoto()));
                    Iam.setId( db.getPersonIdByEmail(eMail) );
                }
                /*
                Iam.setId   ( 1 );
                Iam.setName ( "Miguel Angel Cifredo" );
                Iam.setEmail( "macifredo@gmail.com" );
                */
                getData(actualActivity);
            }
        });
        tr.start();
    }

    private void getData(final Activity activity){
        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
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

    private void dialogConfirmDeleteEvent(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.borrar);
        builder.setMessage(R.string.quiere_eliminar_event);
        builder.setPositiveButton(R.string.eliminar_event, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                deleteEvent();
                Toast.makeText(getApplicationContext(), R.string.eliminado_event, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);

        Dialog dialog = builder.create();
        dialog.show();
    }

    private void deleteEvent(){
        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
                ClsEvent actualEvent = (ClsEvent) Comunicador.getObjeto();
                db.delEvent(actualEvent);
                lstEvents.remove(actualEvent);
                Comunicador.setObjeto(actualEvent);
                runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                getData(actualActivity);
                            }
                        }
                );
            }
        });
        tr.start();
    }


}