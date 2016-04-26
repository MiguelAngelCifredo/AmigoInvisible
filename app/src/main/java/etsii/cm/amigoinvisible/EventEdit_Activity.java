package etsii.cm.amigoinvisible;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

import model.ClsEvent;
import utils.Comunicador;

public class EventEdit_Activity extends AppCompatActivity implements Serializable {

    private ClsEvent actualEvent;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.event_edit, menu);
        if (actualEvent.getData_id_event() == 0) {
            menu.findItem(R.id.opcEventDelete).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.opcEventDelete) {
            // TODO Borrar el evento actual.
        }
        if (id == R.id.opcEventSave) {
            // TODO guardar la edición del evento.
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actualEvent = (ClsEvent) Comunicador.getObjeto();
        setTitle(actualEvent.getData_name());
        setContentView(R.layout.activity_event_detail);
        showData();
    }

    public void showData(){

        ImageView imgVwEventPhoto    = (ImageView)findViewById(R.id.imgVwEventPhoto);
        TextView  txtVwEventDate     = (TextView) findViewById(R.id.txtVwEventDate);
        TextView  txtVwEventTime     = (TextView) findViewById(R.id.txtVwEventTime);
        TextView  txtVwEventPlace    = (TextView) findViewById(R.id.txtVwEventPlace);
        TextView  txtVwEventMaxPrice = (TextView) findViewById(R.id.txtVwEventMaxPrice);
        try{
            if (actualEvent.getData_photo() != null) {
                imgVwEventPhoto.setImageBitmap(actualEvent.getData_photo());
            }
            txtVwEventDate.setText(" " + actualEvent.getData_date_text());
            txtVwEventTime.setText(actualEvent.getData_date_time());
            txtVwEventPlace.setText(actualEvent.getData_place());
            txtVwEventMaxPrice.setText(actualEvent.getData_max_Price().toString() + " €");
        } catch (Exception e){;}

    }

}