package etsii.cm.amigoinvisible;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;

import dbms.RunInDB;
import model.ClsEvent;
import model.ClsMyFriend;

public class MyFriend_Activity extends AppCompatActivity implements Serializable {

    private RunInDB db = new RunInDB();
    private ClsMyFriend miAmigo;
    private ListView listado;
    private ClsEvent eventoActual;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friend);
        listado = (ListView) findViewById(R.id.listItemView);

        eventoActual = (ClsEvent) Comunicador.getObjeto();

        //ImageView imgEvent = (ImageView)findViewById(R.id.imgVwFriend);
        //imgEvent.setImageBitmap(eventoActual.getData_photo());

        obtieneDatosAmigo();

        listado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                Intent nextView = new Intent(getApplicationContext(), WishDetail_Activity.class);
                Comunicador.setObjeto(miAmigo.getData_wish().get(i));
                startActivity(nextView);
            }
        });
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
                                mostrarDatos();
                            }
                        }
                );
            }
        });
        tr.start();
    }
    public void mostrarDatos(){

        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        TextView txtVwMylistIs = (TextView) findViewById(R.id.txtVwMylistIs);
        txtVwMylistIs.setText("Mi lista de deseos es:");

        TextView txtFriendName = (TextView) findViewById(R.id.txtVwFriendName);
        txtFriendName.setText(miAmigo.getData_person().getData_name());

        ImageView imgWishPhoto = (ImageView)findViewById(R.id.imgVwFriendPhoto);
        imgWishPhoto.setImageBitmap(miAmigo.getData_person().getData_photo());

        txtFriendName.setText(miAmigo.getData_person().getData_name());
        Adaptador_lista_deseos adapter = new Adaptador_lista_deseos(this, miAmigo);
        listado.setAdapter(adapter);

    }
}