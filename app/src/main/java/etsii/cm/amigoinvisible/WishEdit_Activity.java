package etsii.cm.amigoinvisible;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

import dbms.getInfo;
import model.ClsWish;

public class WishEdit_Activity extends AppCompatActivity implements Serializable{
    private getInfo   db = new getInfo();
    private ClsWish   wishActual;
    private ImageView imgPhoto;
    private TextView  txtText;
    private TextView  txtDescription;
    private Button    btnSave;
    private Button    btnDelete;
    private String    selectedImagePath;

    private static final int SELECT_PICTURE = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_wish_edit);

        imgPhoto       = (ImageView) findViewById(R.id.imgVwWishEditPhoto);
        txtText        = (TextView)  findViewById(R.id.txtVwWishEditText);
        txtDescription = (TextView)  findViewById(R.id.txtVwWishEditDescription);
        btnSave        = (Button)    findViewById(R.id.btnWishEditSave);
        btnDelete      = (Button)    findViewById(R.id.btnWishEditDelete);

        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveWish();
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deleteWish();
                finish();
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

        wishActual = (ClsWish) Comunicador.getObjeto();

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
        wishActual.setData_text(txtText.getText().toString());
        wishActual.setData_description(txtDescription.getText().toString());
        wishActual.setData_photo(((BitmapDrawable) imgPhoto.getDrawable()).getBitmap());
        Comunicador.setObjeto(wishActual);
        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
                if (wishActual.getData_text().length()>0)
                    if (wishActual.getData_id_wish()==0)
                        db.insWish(wishActual, I_am.getId());
                    else
                        db.setWish(wishActual);
            }
        });
        tr.start();
    }

    public void deleteWish(){
        wishActual.setData_text(null);
        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
                db.delWish(wishActual.getData_id_wish());
            }
        });
        tr.start();
    }

    public void mostrarDatos(){
        imgPhoto.setImageBitmap(wishActual.getData_photo());
        txtText.setText(wishActual.getData_text());
        txtDescription.setText(wishActual.getData_description());
    }
}
