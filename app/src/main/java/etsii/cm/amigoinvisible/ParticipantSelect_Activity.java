package etsii.cm.amigoinvisible;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import adaptador.ListadoContactos_Adapter;
import dbms.RunInDB;
import model.ClsEvent;
import model.ClsPerson;
import utils.Comunicador;
import utils.Contactos;

public class ParticipantSelect_Activity extends AppCompatActivity implements Serializable {
    private RunInDB db = new RunInDB();
    private ArrayList<ClsPerson> lstContactos;
    private ClsPerson actualPerson;
    private ClsEvent  actualEvent;
    private ListView lstVwContact;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_select);
        actualEvent = (ClsEvent) Comunicador.getObjeto();

        lstVwContact = (ListView) findViewById(R.id.lstVwContact);
        lstVwContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                actualPerson = lstContactos.get(i);
                Toast.makeText(getApplicationContext(), R.string.contacto_añadido, Toast.LENGTH_SHORT).show();
                saveContactAsParticipant();
            }
        });
        lstContactos = Contactos.listContacts;

        if(lstContactos==null)
            System.out.println ("******* NO HAY CONTACTOS");
        else
            for (ClsPerson per: lstContactos)
                System.out.println ("******* " + per);

        showData();
    }

    private void showData(){
        setTitle("Seleccione un contacto (" + lstContactos.size() + ")");
        ListadoContactos_Adapter adapter = new ListadoContactos_Adapter(this, lstContactos);
        lstVwContact.setAdapter(adapter);
    }

    private void saveContactAsParticipant(){
        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
                Integer id_person = db.getAccount(actualPerson.getData_email());
                if (id_person == 0){
                    db.insPerson(actualPerson);
                    id_person = db.getAccount(actualPerson.getData_email());
                }
                db.insParticipant(actualEvent, id_person);
                finish();
            }
        });
        tr.start();
    }

}
