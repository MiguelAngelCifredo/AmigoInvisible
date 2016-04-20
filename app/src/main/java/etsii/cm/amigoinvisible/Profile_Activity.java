package etsii.cm.amigoinvisible;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import adaptador.ListadoDeseos_Adapter;
import comparator.WishByText;
import dbms.RunInDB;
import model.ClsMyFriend;
import model.ClsPerson;
import model.ClsWish;
import utils.Comunicador;
import utils.Iam;

public class Profile_Activity extends AppCompatActivity implements Serializable {

    private RunInDB db = new RunInDB();
    private ClsMyFriend personActual;
    private ImageView   imgVwProfilePhoto;
    private TextView    txtVwProfileName;
    private Button      btnAddW;
    private ListView    listado;
    private String      selectedImagePath;

    private static final int SELECT_PICTURE = 1;
    private static boolean add = false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save) {
            saveData();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        imgVwProfilePhoto = (ImageView) findViewById(R.id.imgVwProfilePhoto);
        txtVwProfileName  = (TextView)  findViewById(R.id.txtVwProfileName);
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

        imgVwProfilePhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
            }
        });

        getData();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Si se vuelve de haber borrado o editado
        if (personActual!=null){
            for (int i=0; i< personActual.getData_wish().size(); i++) {
                if (personActual.getData_wish().get(i).getData_text() == null)
                    personActual.getData_wish().remove(i);
            }
            showData();
        }
        // Si se vuelve de haber añadido
        if (add){
            add = false;
            ClsWish wishActual = (ClsWish) Comunicador.getObjeto();
            if (wishActual.getData_text().length() > 0) {
                personActual.getData_wish().add(wishActual);
                Collections.sort(personActual.getData_wish(), new WishByText());
            }
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                imgVwProfilePhoto.setImageURI(selectedImageUri);
                personActual.getData_person().setData_photo(((BitmapDrawable) imgVwProfilePhoto.getDrawable()).getBitmap());
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

    public void getData(){
        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
                //ClsPerson p = db.getPerson(Iam.getId());
                //ArrayList<ClsWish> lw = db.getListWishes(Iam.getId());
                personActual = new ClsMyFriend(db.getPerson(Iam.getId()),db.getListWishes(Iam.getId()));
                runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                showData();
                            }
                        }
                );
            }
        });
        tr.start();
    }

    public void saveData(){
        ClsPerson p = personActual.getData_person();
        p.setData_name(txtVwProfileName.getText().toString());
        p.setData_photo(((BitmapDrawable) imgVwProfilePhoto.getDrawable()).getBitmap());
        personActual.setData_person(p);

        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
                db.setPerson(personActual.getData_person());
            }
        });
        tr.start();
    }

    public void showData(){
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        if (personActual.getData_person().getData_photo() != null)
            imgVwProfilePhoto.setImageBitmap(personActual.getData_person().getData_photo());

        txtVwProfileName.setText(personActual.getData_person().getData_name());

        ListadoDeseos_Adapter adapter = new ListadoDeseos_Adapter(this, personActual);
        listado.setAdapter(adapter);
    }

}