package etsii.cm.amigoinvisible;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import dbms.getInfo;
import model.clsEvent;

public class EventsActivity extends AppCompatActivity {

    public ArrayList<String> dataToList = new ArrayList<>();
    public getInfo db = new getInfo();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        muestraListaEventos();
        ListView lv = (ListView) findViewById(R.id.lstVwEvent);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> customerAdapter, View footer, int selectedInt, long selectedLong) {
                System.out.println("Se ha seleccionado el item numero " + selectedInt);
                //String listChoice = (lstVwEvent.getItemAtPosition(selectedInt));

            }
        });
    }

    public void muestraListaEventos (){
        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
                dataToList.clear();
                ArrayList<clsEvent> lstEvents = db.getListEvents();
                for(clsEvent objEvent : lstEvents) {
                    dataToList.add( objEvent.getData_name()
                                  + " ("
                                  + db.getListParticipants(objEvent.getData_id_event()).size()
                                  + ")"
                    );
                }
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
