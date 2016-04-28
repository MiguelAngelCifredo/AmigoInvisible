package etsii.cm.amigoinvisible;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.Serializable;
import java.util.Calendar;

import model.ClsEvent;
import utils.Comunicador;

public class EventEdit_Activity extends AppCompatActivity implements Serializable {

    private ClsEvent  actualEvent;
    private ImageView imgEventPhoto;
    private String    selectedImagePath;
    private static final int DIALOG_ID      = 0;
    private static final int DIALOG_ID_HOUR = 999;
    private static final int SELECT_PICTURE = 1;

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

    public void showData(){
        TextView  txtEventDate     = (TextView) findViewById(R.id.txtEventDate);
        TextView  txtEventTime     = (TextView) findViewById(R.id.txtEventTime);
        TextView  txtEventPlace    = (TextView) findViewById(R.id.txtEventPlace);
        TextView  txtEventMaxPrice = (TextView) findViewById(R.id.txtEventMaxPrice);
        try{
            if (actualEvent.getData_photo() != null) {
                imgEventPhoto.setImageBitmap(actualEvent.getData_photo());
            }
            txtEventDate.setText(actualEvent.getData_date_text());
            txtEventTime.setText(actualEvent.getData_date_time());
            txtEventPlace.setText(actualEvent.getData_place());
            txtEventMaxPrice.setText(actualEvent.getData_max_Price().toString() + " €");
        } catch (Exception e){
            System.out.println("******  FALLO " + e.getMessage());}

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

}