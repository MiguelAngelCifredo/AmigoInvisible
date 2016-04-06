package etsii.cm.amigoinvisible;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import dbms.getInfo;
import model.clsEvent;
import model.clsPerson;

public class MainActivity extends AppCompatActivity {

    public ArrayList<String> dataToList = new ArrayList<>();
    public getInfo db = new getInfo();
    String foto = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btListarOnClick(View view) {
        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {

                dataToList.clear();

                ArrayList<clsEvent> lstEvents = db.getListEvents();
                for(clsEvent objEvent : lstEvents) { dataToList.add(objEvent.getData_name()); }

                /*
                ArrayList<clsPerson> lstPersons = db.getListParticipants();

                for(clsPerson objPerson : lstPersons) {
                    dataToList.add(objPerson.getData_name() + " - " + objPerson.getData_email());
                }
                */

                //foto = dbms.getPersonPhoto();

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
            ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataToList);
            ListView lista = (ListView) findViewById(R.id.lvListado);
            try { lista.setAdapter(adaptador); } catch (Exception e) {}
    }

}
//end