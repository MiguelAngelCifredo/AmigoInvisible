package etsii.cm.amigoinvisible;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import dbms.getInfo;
import model.ClsEvent;
import model.ClsParticipant;
import model.ClsWish;

public class InfoEventActivity extends AppCompatActivity implements Serializable {

    private getInfo db = new getInfo();
    private ListView listado;
    private ClsEvent eventoActual;
    private ArrayList<ClsParticipant> lstParticipants;
    private ArrayList<String> titulo = new ArrayList<>();
    private ArrayList<Bitmap> photo  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_event);

        listado = (ListView) findViewById(R.id.lstVwParticipants);
        //eventoActual = (ClsEvent) Comunicador.getObjeto();
        obtieneDatosEvento();
    }

    public void obtieneDatosEvento (){
        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {

                ArrayList<ClsEvent> listaEventos = db.getListEvents();
                eventoActual = listaEventos.get(0);

                lstParticipants = db.getListParticipants(eventoActual.getData_id_event());
                for(ClsParticipant objParticipant : lstParticipants) {
                    titulo.add( objParticipant.getData_person().getData_name() );
                    photo.add(objParticipant.getData_person().getData_photo());
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

        ImageView imgEventPhoto = (ImageView)findViewById(R.id.imgVwEventPhoto);
        imgEventPhoto.setImageBitmap(eventoActual.getData_photo());

        TextView txtEventDate = (TextView) findViewById(R.id.txtVwEventDate);
        txtEventDate.setText(eventoActual.getData_date());

        TextView txtEventTime = (TextView) findViewById(R.id.txtVwEventTime);
        txtEventTime.setText(eventoActual.getData_date());

        TextView txtEventPlace = (TextView) findViewById(R.id.txtVwEventPlace);
        txtEventPlace.setText(eventoActual.getData_place());

        TextView txtEventMaxPrice = (TextView) findViewById(R.id.txtVwEventMaxPrice);
        txtEventMaxPrice.setText(eventoActual.getData_max_Price().toString() + " €");

        Adaptador_ListaIconoTexto adapter = new Adaptador_ListaIconoTexto(this, titulo, photo);
        listado.setAdapter(adapter);
    }

}
