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

import dbms.RunInDB;
import model.ClsEvent;
import model.ClsParticipant;
import utils.Empareja;

public class EventList_Activity extends AppCompatActivity implements Serializable {
    private RunInDB db = new RunInDB();
    private ArrayList<ClsEvent> lstEvents;
    private ListView listado;
    private ArrayList<ClsParticipant> lsParticipants;

    // possibleEmail obtiene el email del terminal
    private String possibleEmail;
    // VALOR_PARA_DISTINGUIR_LA_PETICION = 66 identifica la petición de permisos

    final static int VALOR_PARA_DISTINGUIR_LA_PETICION = 66;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        //obtieneListaParticipantes(); //finish();

        // Acceso a la cuenta de email principal del terminal
        // la variable será possibleEmail
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS)!= PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.GET_ACCOUNTS)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.GET_ACCOUNTS}, VALOR_PARA_DISTINGUIR_LA_PETICION);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            android.accounts.AccountManager am = android.accounts.AccountManager.get(this);
            android.accounts.Account[] accounts = am.getAccounts();

            for(Account acc : accounts) {
                System.out.println(acc.toString());
                //acc.name es el nombre de la cuenta primaria de google.com del terminal
                if(acc.type.matches("com.google")) possibleEmail = acc.name.toString(); //System.out.println("################" + acc.name.toString());
                //System.out.println("@@@ cuenta: " + acc.name.toString());
            }
        } // end Acceso a la cuenta de email principal del terminal

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
    // para generar un menu según se acepte dar o no permiso
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case VALOR_PARA_DISTINGUIR_LA_PETICION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }//end onRequestPermission

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
        Adaptador_Lista_Eventos adapter = new Adaptador_Lista_Eventos(this, lstEvents);
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