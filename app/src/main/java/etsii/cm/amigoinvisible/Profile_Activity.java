package etsii.cm.amigoinvisible;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import dbms.getInfo;
import model.ClsMyFriend;
import model.ClsPerson;
import model.ClsWish;

public class Profile_Activity extends AppCompatActivity implements Serializable {

    private getInfo db = new getInfo();
    private ClsMyFriend personActual;
    private ImageView   imgPhoto;
    private TextView    txtName;
    private Button      btnSave;
    private Button      btnAddW;
    private ListView    listado;
    private String      selectedImagePath;

    private static final int SELECT_PICTURE = 1;
    private static boolean add = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //***************************************
        I_am.setData(2, "manolito@gmail.com");
        //***************************************

        setContentView(R.layout.activity_profile);

        imgPhoto = (ImageView) findViewById(R.id.imgVwProfilePhoto);
        txtName  = (TextView)  findViewById(R.id.txtVwProfileName);
        btnSave  = (Button)    findViewById(R.id.btnProfileSave);
        btnAddW  = (Button)    findViewById(R.id.btnAddWish);

        listado  = (ListView)  findViewById(R.id.lstVwWishes);

        listado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                Intent nextView = new Intent(getApplicationContext(), WishEdit_Activity.class);
                Comunicador.setObjeto(personActual.getData_wish().get(i));
                startActivity(nextView);
            }
        });

        btnAddW.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent nextView = new Intent(getApplicationContext(), WishEdit_Activity.class);
                Comunicador.setObjeto(new ClsWish(0,"","",null,"N"));
                add = true;
                startActivity(nextView);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                savePerson();
            }
        });

        imgPhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
            }
        });

        //personActual = (ClsPerson) Comunicador.getObjeto();
        leerPerson();
    }

    @Override
    protected void onResume() {
        super.onResume();
/*
*
* *     REVISAR:
* *
* *     1 - Si la activity se carga por primera vez, no hacer nada
* *     2 - Si venimos de haber borrado, eliminarlo del ArrayList
* *     3 - Si venimos de haber insertado, lo temamos por el Comunicador y lo añadirmos al ArrayList
* *
* *
* *
 */
        // Si se ha borrado el wish se quita de la lista
        if (personActual!=null)
            for (int i=0; i< personActual.getData_wish().size(); i++) {
                if (personActual.getData_wish().get(i).getData_text() == null)
                    personActual.getData_wish().remove(i);
            }
        if (add){
            System.out.println("**************** VIENES DE HABER AÑADIDO");
            add = false;
            ClsWish wishActual = (ClsWish) Comunicador.getObjeto();
            personActual.getData_wish().add(wishActual);
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                imgPhoto.setImageURI(selectedImageUri);
                selectedImagePath = getPath(selectedImageUri);
                System.out.println("****** La foto seleccionada es: " + selectedImagePath);
            }
        }
    }

    public String getPath(Uri contentUri) {
        String res = null;
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null);
        if(cursor.moveToFirst()){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        if (cursor != null) {
            cursor.close();
        }
        return res;
    }

    public void leerPerson(){
        System.out.println("************* LEYENDO DATOS" );
        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
                ClsPerson p = db.getPerson(I_am.getId());
                ArrayList<ClsWish> l = db.getListWishes(I_am.getId());
                personActual = new ClsMyFriend(p,l);
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

    public void savePerson(){
        ClsPerson p = personActual.getData_person();
        p.setData_name(txtName.getText().toString());
        p.setData_photo(((BitmapDrawable) imgPhoto.getDrawable()).getBitmap());
        personActual.setData_person(p);

        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
                db.setPerson(personActual.getData_person());
            }
        });
        tr.start();
    }

    public void mostrarDatos(){
        System.out.println("************* MOSTRANDO DATOS" );
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        imgPhoto.setImageBitmap(personActual.getData_person().getData_photo());
        txtName.setText(personActual.getData_person().getData_name());

        Adaptador_lista_deseos adapter = new Adaptador_lista_deseos(this, personActual);
        listado.setAdapter(adapter);
    }

}