package etsii.cm.amigoinvisible;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dbms.RunInDB;
import model.ClsEvent;
import utils.Comunicador;
import utils.Iam;
import utils.Photo;

public class EventEdit_Activity extends AppCompatActivity implements Serializable {
    private RunInDB db = new RunInDB();
    private ClsEvent  actualEvent;
    private ImageView imgEventPhoto;
    private TextView  txtEventName;
    private TextView  txtEventMaxPrice;
    private TextView  txtEventPlace;

    private static final int DIALOG_ID      = 0;
    private static final int DIALOG_ID_HOUR = 999;
    private static final int SELECT_PICTURE = 1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.event_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.opcEventSave) {
            hideSoftKeyboard();
            saveEvent();
            Toast.makeText(EventEdit_Activity.this, "Evento guardado", Toast.LENGTH_SHORT).show();
            Comunicador.setObjeto(actualEvent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actualEvent = (ClsEvent) Comunicador.getObjeto();

        setTitle(actualEvent.getData_name());
        setContentView(R.layout.activity_event_edit);

        txtEventName     = (TextView) findViewById(R.id.txtEventName);
        txtEventMaxPrice = (TextView) findViewById(R.id.txtEventMaxPrice);
        txtEventPlace    = (TextView) findViewById(R.id.txtEventPlace);

        imgEventPhoto = (ImageView)findViewById(R.id.imgEventPhoto);
        imgEventPhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
            }
        });

        showDialogOnButtonClick();
        showData();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                imgEventPhoto.setImageURI(selectedImageUri);
                actualEvent.setData_photo(((BitmapDrawable) imgEventPhoto.getDrawable()).getBitmap());
                actualEvent.setData_file_path(Photo.getPath(selectedImageUri, this));
            }
        }
    }

    public void showData(){
        TextView txtEventDate = (TextView) findViewById(R.id.txtEventDate);
        TextView txtEventTime = (TextView) findViewById(R.id.txtEventTime);
        try{
            if (actualEvent.getData_photo() != null) {
                imgEventPhoto.setImageBitmap(actualEvent.getData_photo());
            }
            txtEventName.setText(actualEvent.getData_name());
            txtEventDate.setText(actualEvent.getData_date_text());
            txtEventTime.setText(actualEvent.getData_date_time());
            txtEventPlace.setText(actualEvent.getData_place());
            txtEventMaxPrice.setText(actualEvent.getData_max_price().toString());
        } catch (Exception e){;}
    }

    public void saveEvent(){
        actualEvent.setData_photo(((BitmapDrawable) imgEventPhoto.getDrawable()).getBitmap());
        String name = txtEventName.getText().toString();
        actualEvent.setData_name((name.length() == 0 ? "Nuevo evento" : name));
        if (actualEvent.getData_date().equals("")) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            actualEvent.setData_date(formatter.format(new Date()));
        }
        int price = 0;
        try { price = Integer.parseInt(txtEventMaxPrice.getText().toString()); } catch (Exception e) {;}
        actualEvent.setData_max_price(price);
        actualEvent.setData_place(txtEventPlace.getText().toString());
        Comunicador.setObjeto(actualEvent);

        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
                if (actualEvent.getData_id_event() == 0) {
                    db.insEvent(actualEvent);
                    actualEvent.setData_id_event(db.cntEvent());
                    db.insParticipant(actualEvent, Iam.getId());
                }else{
                    db.setEvent(actualEvent);
                }
            }
        });
        tr.start();
    }

   public void showDialogOnButtonClick(){
        findViewById(R.id.btnEventDate).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });
        findViewById(R.id.btnEventTime).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_ID_HOUR);
            }
        });
    }

    protected Dialog onCreateDialog(int id){
        if (actualEvent.getData_date().equals("")) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            actualEvent.setData_date(formatter.format(new Date()));
        }
        if(id == DIALOG_ID){
            return new DatePickerDialog(this, dpickerLister, actualEvent.getDate().get(Calendar.YEAR), actualEvent.getDate().get(Calendar.MONTH), actualEvent.getDate().get(Calendar.DAY_OF_MONTH));
        }
        if(id == DIALOG_ID_HOUR){
            return new TimePickerDialog(this, tpickerLister, actualEvent.getDate().get(Calendar.HOUR_OF_DAY), actualEvent.getDate().get(Calendar.MINUTE), DateFormat.is24HourFormat(getApplicationContext()));
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerLister = new DatePickerDialog.OnDateSetListener(){
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
        actualEvent.setDate(dayOfMonth, monthOfYear + 1, year);
        showData();
        }
    };

    private TimePickerDialog.OnTimeSetListener tpickerLister = new TimePickerDialog.OnTimeSetListener(){
        public void onTimeSet(TimePicker view, int hour, int minutes){
        actualEvent.setTime(hour, minutes);
        showData();
        }
    };

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}