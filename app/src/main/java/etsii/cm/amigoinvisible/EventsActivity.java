package etsii.cm.amigoinvisible;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

import dbms.getInfo;
import model.ClsEvent;
import model.ClsMyFriend;

public class EventsActivity extends AppCompatActivity implements Serializable {
    private static final String LogTAG = " MACC ACTIVITY";
    public ArrayList<String> dataToList = new ArrayList<>();
    public getInfo db = new getInfo();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        Log.d(LogTAG, "Voy a MuestraEventos ->");
        muestraListaEventos();
        final ListView lv = (ListView) findViewById(R.id.lstVwEvent);

        //Codigo para seleccionar y obtener el evento seleccionado
        if( lv != null){
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> customerAdapter, View footer, int selectedInt, long selectedLong) {

                    //System.out.println("Se ha seleccionado el item numero " + selectedInt);
                    //System.out.println("****************" + db.getListEvents().get(selectedInt).getData_name());

                    ArrayList<ClsEvent> lista = db.getListEvents();
                    ClsEvent c = new ClsEvent(lista.get(selectedInt));

                    //System.out.println("***************** " + c.toString());

                    //Creo el evento que voy a mandar a la otra vista
                    Intent miIntent = new Intent(getApplicationContext(), ViewEventActivity.class);

                    System.out.println("+++++++++++++++++++++++++" + c.toString());
                    miIntent.putExtra("idEvent", c.getData_id_event());
                    miIntent.putExtra("nameEvent", c.getData_name());
                    miIntent.putExtra("dateEvent", c.getData_date());
                    miIntent.putExtra("placeEvent", c.getData_place());
                    miIntent.putExtra("priceEvent", c.getData_max_Price());
                    startActivity(miIntent);

                }
            });
        }

    }

    public void muestraListaEventos (){
        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
                Integer eventoSeleccionado = 1;
                ClsMyFriend miAmigo = db.getMyFriend(eventoSeleccionado);
                System.out.println("*****  AMIGO: " + miAmigo.getData_person().getData_name());

                dataToList.clear();
                ArrayList<ClsEvent> lstEvents = db.getListEvents();
                for(ClsEvent objEvent : lstEvents) {
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