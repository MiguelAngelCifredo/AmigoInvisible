package etsii.cm.amigoinvisible;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;

import dbms.getInfo;
import model.ClsEvent;
import model.ClsMyFriend;

public class ViewEventActivity extends AppCompatActivity implements Serializable {

    private getInfo db = new getInfo();
    private ClsMyFriend miAmigo;
    ClsEvent eventoActual;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);

        eventoActual = (ClsEvent) Comunicador.getObjeto();

        TextView txtEventName = (TextView)findViewById(R.id.txtVwEventName);
        txtEventName.setText(eventoActual.getData_name());
        ImageView imgEvent = (ImageView)findViewById(R.id.imgVwEvent);
        imgEvent.setImageBitmap(eventoActual.getData_photo());

        obtieneDatosAmigo();
    }
    public void obtieneDatosAmigo (){
        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
                miAmigo = db.getMyFriend(eventoActual.getData_id_event(), "macifredo@gmail.com");
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
        TextView txtFriendName = (TextView) findViewById(R.id.txtVwFriendName);
        txtFriendName.setText(miAmigo.getData_person().getData_name());
        //ListaAdapter adapter = new ListaAdapter(this, titulo, photo);
        //listado.setAdapter(adapter);
    }
}