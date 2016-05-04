package etsii.cm.amigoinvisible;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Collections;

import adaptador.ListadoDeseos_Adapter;
import comparator.WishByText;
import dbms.RunInDB;
import model.ClsMyFriend;
import model.ClsPerson;
import model.ClsWish;
import utils.Comunicador;
import utils.Iam;
import utils.Photo;

public class Profile_Activity extends AppCompatActivity implements Serializable {

    private RunInDB db = new RunInDB();
    private ClsMyFriend actualPerson;
    private ClsWish     actualWish;
    private ImageView   imgVwProfilePhoto;
    private TextView    txtVwProfileName;
    private ListView    lstVwWishes;
    private FloatingActionButton btnAddWish;

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
        if (id == R.id.opcProfileSave) {
            saveData();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        setTitle("Mi perfil");

        imgVwProfilePhoto = (ImageView) findViewById(R.id.imgVwProfilePhoto);
        txtVwProfileName  = (TextView)  findViewById(R.id.txtVwProfileName);
        lstVwWishes       = (ListView)  findViewById(R.id.lstVwWishes);
        btnAddWish        = (FloatingActionButton) findViewById(R.id.btnAddWish);

        lstVwWishes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                Intent nextView = new Intent(getApplicationContext(), WishEdit_Activity.class);
                Comunicador.setObjeto(actualPerson.getData_wish().get(i));
                startActivity(nextView);
            }
        });

        lstVwWishes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView adapterView, View view, int i, long l) {
                actualWish = actualPerson.getData_wish().get(i);
                dialogConfirmDeleteWish();
                return true;
            }
        });

        btnAddWish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                add = true;
                Comunicador.setObjeto(new ClsWish(0,"","",null,"N"));
                Intent nextView = new Intent(getApplicationContext(), WishEdit_Activity.class);
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
        if (actualPerson !=null){
            for (int i = 0; i< actualPerson.getData_wish().size(); i++) {
                if (actualPerson.getData_wish().get(i).getData_text() == null)
                    actualPerson.getData_wish().remove(i);
            }
            showData();
        }
        // Si se vuelve de haber añadido
        if (add){
            add = false;
            ClsWish wishActual = (ClsWish) Comunicador.getObjeto();
            if (wishActual.getData_text().length() > 0) {
                actualPerson.getData_wish().add(wishActual);
                Collections.sort(actualPerson.getData_wish(), new WishByText());
            }
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                imgVwProfilePhoto.setImageURI(selectedImageUri);
                actualPerson.getData_person().setData_photo(((BitmapDrawable) imgVwProfilePhoto.getDrawable()).getBitmap());
                actualPerson.getData_person().setData_file_path(Photo.getPath(selectedImageUri, this));
            }
        }
    }

    private void getData(){
        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
                actualPerson = new ClsMyFriend(db.getPerson(Iam.getId()),db.getListWishes(Iam.getId()));
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

    private void saveData(){
        ClsPerson p = actualPerson.getData_person();
        p.setData_name(txtVwProfileName.getText().toString());
        p.setData_photo(((BitmapDrawable) imgVwProfilePhoto.getDrawable()).getBitmap());
        actualPerson.setData_person(p);

        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
                db.setPerson(actualPerson.getData_person());
            }
        });
        tr.start();
    }

    private void showData(){
       try{ findViewById(R.id.loadingPanel).setVisibility(View.GONE); } catch (Exception e) {;}

        if (actualPerson.getData_person().getData_photo() != null)
            imgVwProfilePhoto.setImageBitmap(actualPerson.getData_person().getData_photo());

        txtVwProfileName.setText(actualPerson.getData_person().getData_name());

        ListadoDeseos_Adapter adapter = new ListadoDeseos_Adapter(this, actualPerson);
        lstVwWishes.setAdapter(adapter);
    }

    private void dialogConfirmDeleteWish(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.borrar);
        builder.setMessage(R.string.quiere_eliminar_wish);
        builder.setPositiveButton(R.string.eliminar_wish, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                deleteWish();
                showData();
                Toast.makeText(getApplicationContext(), R.string.eliminado_wish, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);

        Dialog dialog = builder.create();
        dialog.show();
    }

    private void deleteWish(){
        actualPerson.getData_wish().remove(actualWish);
        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
                db.delWish(actualWish.getData_id_wish());
            }
        });
        tr.start();
    }
}