package etsii.cm.amigoinvisible;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;

import model.ClsEvent;
import utils.Comunicador;


public class ParticipantList_Activity extends AppCompatActivity implements Serializable {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_participant);
    }

}
