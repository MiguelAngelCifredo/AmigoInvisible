package etsii.cm.amigoinvisible;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import dbms.MainDBMS;
import dbms.clsEvent;

public class EventsActivity extends AppCompatActivity {

    public ArrayList<String> dataToList = new ArrayList<>();
    public MainDBMS dbms = new MainDBMS();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        muestraEventos();
    }

    public void muestraEventos (){
        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {

                dataToList.clear();

                ArrayList<clsEvent> lstEvents = dbms.getEvents();
                for(clsEvent objEvent : lstEvents) { dataToList.add(objEvent.getData_name()); }
                System.out.println("**********************Entrando en DB" + lstEvents.size());
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
        ListView lista = (ListView) findViewById(R.id.lstVwEvent);
        try { lista.setAdapter(adaptador); } catch (Exception e) {}
    }
}
