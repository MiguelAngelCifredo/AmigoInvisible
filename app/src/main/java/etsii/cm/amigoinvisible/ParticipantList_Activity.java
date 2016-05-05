package etsii.cm.amigoinvisible;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import adaptador.ListadoParticipantes_Adapter;
import dbms.RunInDB;
import model.ClsEvent;
import model.ClsParticipant;
import model.ClsWish;
import utils.Comunicador;
import utils.Empareja;
import utils.Iam;

public class ParticipantList_Activity extends AppCompatActivity implements Serializable {

    private RunInDB db = new RunInDB();
    private ListView lstVwParticipants;
    private ArrayList<ClsParticipant> lstParticipants;
    private ClsEvent actualEvent;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.participant_list, menu);
        findViewById(R.id.btnAddParticipant).setVisibility(View.INVISIBLE);
        if (!Iam.admin(actualEvent)) {
            menu.findItem(R.id.opcParticipantMatch).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.opcParticipantMatch) {
            dialogConfirmMatchParticipants();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_list);
        actualEvent = (ClsEvent) Comunicador.getObjeto();

        lstVwParticipants = (ListView) findViewById(R.id.lstVwParticipants);
        lstVwParticipants.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView adapterView, View view, int i, long l) {
                if (Iam.admin(actualEvent)) {
                    Comunicador.setObjeto(lstParticipants.get(i));
                    dialogConfirmDeleteParticipant();
                }
                return true;
            }
        });

        findViewById(R.id.btnAddParticipant).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent nextView = new Intent(getApplicationContext(), ParticipantSelect_Activity.class);
                startActivity(nextView);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData(){
        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
            lstParticipants = db.getListParticipants(actualEvent.getData_id_event());
            runOnUiThread(
                    new Runnable() {
                        @Override
                        public void run() {
                            showData();
                        }
                    }
            );
            }
        });
        setTitle("Buscando participantes...");
        tr.start();
    }

    private void showData(){
        try {findViewById(R.id.loadingPanel).setVisibility(View.GONE);} catch(Exception e){;}
        if (Iam.admin(actualEvent)) {
            findViewById(R.id.btnAddParticipant).setVisibility(View.VISIBLE);
        }
        setTitle("Participantes (" + lstParticipants.size() + ")");
        ListadoParticipantes_Adapter adapter = new ListadoParticipantes_Adapter(this, lstParticipants);
        lstVwParticipants.setAdapter(adapter);
    }

    private void dialogConfirmDeleteParticipant(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.borrar);
        builder.setMessage(R.string.quiere_eliminar_participant);
        builder.setPositiveButton(R.string.eliminar_participant, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                deleteParticipant();
                Toast.makeText(getApplicationContext(), R.string.eliminado_participant, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);

        Dialog dialog = builder.create();
        dialog.show();
    }

    private void deleteParticipant(){
        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
                ClsParticipant actualParticipant = (ClsParticipant) Comunicador.getObjeto();
                db.delParticipant(actualParticipant.getData_id_participant());
                lstParticipants.remove(actualParticipant);
                Comunicador.setObjeto(actualEvent);
                runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                showData();
                            }
                        }
                );
            }
        });
        tr.start();
    }

    private void dialogConfirmMatchParticipants(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.emparejar);
        builder.setMessage(R.string.quiere_emparejar_participant);
        builder.setPositiveButton(R.string.emparejar_participant, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                matchParticipants();
                Toast.makeText(getApplicationContext(), R.string.emparejados_participant, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);

        Dialog dialog = builder.create();
        dialog.show();
    }

    private void matchParticipants(){
        Empareja.Participantes(lstParticipants);
        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
                db.matchParticipants(lstParticipants);
            }
        });
        tr.start();
    }

}