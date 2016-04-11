package etsii.cm.amigoinvisible;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;

public class InfoEventActivity extends AppCompatActivity implements Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_event);
    }
}
