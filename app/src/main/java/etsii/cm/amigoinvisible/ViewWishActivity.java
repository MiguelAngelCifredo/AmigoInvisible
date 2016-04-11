package etsii.cm.amigoinvisible;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

import model.ClsWish;

public class ViewWishActivity extends AppCompatActivity implements Serializable{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_detail);

        ClsWish deseoActual = (ClsWish) Comunicador.getObjeto();

        //TextView txtWishText = (TextView)findViewById(R.id.txtWishText);
        //txtWishText.setText(deseoActual.getData_text());

        ImageView imgWishPhoto = (ImageView)findViewById(R.id.imgVwWishPhoto);
        imgWishPhoto.setImageBitmap(deseoActual.getData_photo());

        TextView txtWishDescription = (TextView)findViewById(R.id.txtVwWishDescription);
        txtWishDescription.setText(deseoActual.getData_description());

    }

}
