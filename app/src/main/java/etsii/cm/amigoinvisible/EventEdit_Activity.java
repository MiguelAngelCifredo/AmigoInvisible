package etsii.cm.amigoinvisible;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Calendar;

import model.ClsEvent;
import utils.Comunicador;

public class EventEdit_Activity extends AppCompatActivity implements Serializable {

    private ClsEvent actualEvent;
    private ImageButton btnEditDate;
    private ImageButton btnEditHour;
    private int year_x,month_x,day_x,hour_x,minutes_x;
    private TextView txtFecha;
    static final int DIALOG_ID = 0;
    static final int DIALOG_ID_HOUR = 999;



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
        setContentView(R.layout.activity_event_edit);


        final Calendar calendar = Calendar.getInstance();
        year_x = calendar.get(Calendar.YEAR);
        month_x = calendar.get(Calendar.MONTH);
        day_x = calendar.get(Calendar.DAY_OF_MONTH);

        showDialogOnButtonClick();
        showDialogOnButtonClick();
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

    public void showDialogOnButtonClick(){
        btnEditDate = (ImageButton) findViewById(R.id.btnEditDate);
        btnEditHour = (ImageButton)findViewById(R.id.btnEditHour);

        btnEditDate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                showDialog(DIALOG_ID);
            }
        });

        btnEditHour.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v){
                showDialog(DIALOG_ID_HOUR);
            }
        });
    }



    protected Dialog onCreateDialog(int id){
        if(id ==DIALOG_ID){
            return new DatePickerDialog(this,dpickerLister,year_x ,month_x,day_x);
        }else if(id == DIALOG_ID_HOUR){
            return new TimePickerDialog(this,tpickerLister,hour_x,minutes_x, DateFormat.is24HourFormat(getApplicationContext()));
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerLister = new DatePickerDialog.OnDateSetListener(){
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
            year_x = year;
            month_x = monthOfYear + 1;
            day_x = dayOfMonth;
            Toast.makeText(EventEdit_Activity.this,day_x+"/"+month_x+"/"+year_x, Toast.LENGTH_SHORT).show();
        }
    };


    //TimePicker

    private TimePickerDialog.OnTimeSetListener tpickerLister = new TimePickerDialog.OnTimeSetListener(){
        public void onTimeSet(TimePicker view, int hour, int minutes){
            hour_x = hour;
            minutes_x = minutes;
            Toast.makeText(EventEdit_Activity.this, hour+" : "+minutes, Toast.LENGTH_SHORT).show();
        }
    };


    }

