package etsii.cm.amigoinvisible;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

import dbms.RunInDB;
import model.ClsWish;
import utils.Comunicador;
import utils.Iam;

public class WishEdit_Activity extends AppCompatActivity implements Serializable{
    private RunInDB db = new RunInDB();
    private ClsWish   actualWish;
    private ImageView imgPhoto;
    private TextView  txtText;
    private TextView  txtDescription;
    private String    selectedImagePath;
    private MenuItem opcWishSave;

    private static final int SELECT_PICTURE = 1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.wish_edit, menu);
        if (actualWish.getData_id_wish() == 0) {
            menu.findItem(R.id.opcWishDelete).setVisible(false);
        }
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.opcWishDelete) {
            abre_dialogo();
            //deleteWish();
            //finish();

        }
        if (id == R.id.opcWishSave) {
            saveWish();
            hideSoftKeyboard();
            finish();
            Toast.makeText(WishEdit_Activity.this, "Deseo guardado", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actualWish = (ClsWish) Comunicador.getObjeto();

        if (actualWish.getData_text().equals("")){
            setTitle("Deseo nuevo");
        } else {
            setTitle(actualWish.getData_text());
        }

        setContentView(R.layout.activity_wish_edit);

        imgPhoto       = (ImageView) findViewById(R.id.imgVwWishEditPhoto);
        txtText        = (TextView)  findViewById(R.id.txtVwWishEditText);
        txtDescription = (TextView)  findViewById(R.id.txtVwWishEditDescription);

        imgPhoto.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View arg0) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
                    }
                });

        mostrarDatos();
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

    public void saveWish(){
        if (txtText.getText().length() > 0) {
            actualWish.setData_text(txtText.getText().toString());
            actualWish.setData_description(txtDescription.getText().toString());
            actualWish.setData_photo(((BitmapDrawable) imgPhoto.getDrawable()).getBitmap());
            Comunicador.setObjeto(actualWish);
            Thread tr = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (actualWish.getData_id_wish() == 0)
                    db.insWish(actualWish, Iam.getId());
                else
                    db.setWish(actualWish);
                }
            });
            tr.start();
        }
    }

    public void deleteWish(){
        actualWish.setData_text(null);
        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
                db.delWish(actualWish.getData_id_wish());
            }
        });
        tr.start();
    }

    public void mostrarDatos(){

        if (actualWish.getData_photo() != null) {
            imgPhoto.setImageBitmap(actualWish.getData_photo());
        }
        if (actualWish.getData_text().length() > 0) {
            txtText.setText(actualWish.getData_text());
        }
        if (actualWish.getData_description().length() > 0) {
            txtDescription.setText(actualWish.getData_description());
        }
    }

    public void abre_dialogo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.borrar);
        builder.setMessage(R.string.quiere_borrar);
        builder.setPositiveButton(R.string.borra, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                deleteWish();
                Toast.makeText(getApplicationContext(), R.string.borrado, Toast.LENGTH_SHORT).show();
                hideSoftKeyboard();
                finish();
            }
        });
        builder.setNegativeButton(android.R.string.cancel,null);

        Dialog dialog = builder.create();
        dialog.show();
    }
    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }


}
