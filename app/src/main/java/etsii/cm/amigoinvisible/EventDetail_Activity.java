package etsii.cm.amigoinvisible;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

import model.ClsEvent;
import utils.Comunicador;
import utils.Iam;

public class EventDetail_Activity extends AppCompatActivity implements Serializable {

    private ClsEvent actualEvent;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.event_detail, menu);
        if (!Iam.admin(actualEvent)) {
            menu.findItem(R.id.opcEventEdit).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.opcEventEdit){
            Intent nextView = new Intent(getApplicationContext(), EventEdit_Activity.class);
            startActivity(nextView);
        }
        if(id == R.id.opcParticipantList){
            Comunicador.setObjeto(actualEvent);
            Intent nextView = new Intent(getApplicationContext(), ParticipantList_Activity.class);
            startActivity(nextView);
        }
        if (id == R.id.opcMyFriend) {
            Comunicador.setObjeto(actualEvent);
            Intent nextView = new Intent(getApplicationContext(), MyFriend_Activity.class);
            startActivity(nextView);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        actualEvent = (ClsEvent) Comunicador.getObjeto();
        showData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualEvent = (ClsEvent) Comunicador.getObjeto();
        showData();
    }

    public void showData(){
        ImageView imgVwEventPhoto    = (ImageView)findViewById(R.id.imgEventPhoto);
        TextView  txtVwEventDate     = (TextView) findViewById(R.id.txtVwEventDate);
        TextView  txtVwEventTime     = (TextView) findViewById(R.id.txtVwEventTime);
        TextView  txtVwEventPlace    = (TextView) findViewById(R.id.txtVwEventPlace);
        TextView  txtVwEventMaxPrice = (TextView) findViewById(R.id.txtVwEventMaxPrice);

        try {
            if (actualEvent.getData_photo() != null) {
                imgVwEventPhoto.setImageBitmap(actualEvent.getData_photo());
            }
        } catch (Exception e) {;}

        setTitle(actualEvent.getData_name());
        txtVwEventDate.setText(" " + actualEvent.getData_date_text());
        txtVwEventTime.setText(actualEvent.getData_date_time());
        txtVwEventPlace.setText(actualEvent.getData_place());
        txtVwEventMaxPrice.setText(actualEvent.getData_max_price().toString() + " €");
    }

}