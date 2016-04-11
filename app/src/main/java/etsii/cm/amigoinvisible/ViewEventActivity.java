package etsii.cm.amigoinvisible;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import dbms.getInfo;
import model.ClsEvent;
import model.ClsMyFriend;
import model.ClsWish;

public class ViewEventActivity extends AppCompatActivity implements Serializable {

    private getInfo db = new getInfo();
    private ClsMyFriend miAmigo;
    private ListView listado;
    private ClsEvent eventoActual;
    private ArrayList<String> titulo = new ArrayList<>();
    private ArrayList<Bitmap> photo  = new ArrayList<>();
    private ArrayList<ClsWish> lstWishes;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        listado = (ListView) findViewById(R.id.listItemView);

        eventoActual = (ClsEvent) Comunicador.getObjeto();

        //ImageView imgEvent = (ImageView)findViewById(R.id.imgVwFriend);
        //imgEvent.setImageBitmap(eventoActual.getData_photo());

        obtieneDatosAmigo();

        listado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                Intent nextView = new Intent(getApplicationContext(), ViewWishActivity.class);
                Comunicador.setObjeto(lstWishes.get(i));
                startActivity(nextView);
            }
        });
    }
    public void obtieneDatosAmigo (){
        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
                miAmigo = db.getMyFriend(eventoActual.getData_id_event(), "macifredo@gmail.com");
                lstWishes = db.getListWishes(miAmigo.getData_person().getData_id_person());
                for(ClsWish objWish : lstWishes) {
                    titulo.add( objWish.getData_text() );
                    photo.add( objWish.getData_photo() );
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
        TextView txtFriendName = (TextView) findViewById(R.id.txtVwFriendName);
        txtFriendName.setText(miAmigo.getData_person().getData_name());

        ImageView imgWishPhoto = (ImageView)findViewById(R.id.imgVwFriendPhoto);
        imgWishPhoto.setImageBitmap(miAmigo.getData_person().getData_photo());

        txtFriendName.setText(miAmigo.getData_person().getData_name());
        Adaptador_ListaIconoTexto adapter = new Adaptador_ListaIconoTexto(this, titulo, photo);
        listado.setAdapter(adapter);
    }
}