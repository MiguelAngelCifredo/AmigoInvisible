package etsii.cm.amigoinvisible;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

import dbms.getInfo;
import model.ClsEvent;
import model.ClsMyFriend;

public class EventsActivity extends AppCompatActivity implements Serializable {
    private ArrayList<String> dataToList = new ArrayList<>();
    private getInfo db = new getInfo();
    private Bitmap resultado;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
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

                //ClsMyFriend miAmigo = db.getMyFriend(eventoSeleccionado);
                //System.out.println("*****  AMIGO: " + miAmigo.getData_person().getData_name());
/*
                System.out.println("***** " + "obteniendo la foto...");
                String foto = db.getPhoto("person", 1);
                byte[] arr = Base64.decode(foto, Base64.DEFAULT);
                resultado = BitmapFactory.decodeByteArray(arr, 0, arr.length);
                System.out.println("***** " + "Foto mostrada.");
*/


                dataToList.clear();
                ArrayList<ClsEvent> lstEvents = db.getListEvents();
                resultado = db.getPhoto("person", 1);
                for(ClsEvent objEvent : lstEvents) {
                    dataToList.add( objEvent.getData_name() );
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

        ImageView imagen = (ImageView) findViewById(R.id.imageView);
        imagen.setImageBitmap(resultado);

        try { lista.setAdapter(adaptador); } catch (Exception e) {}
    }

}