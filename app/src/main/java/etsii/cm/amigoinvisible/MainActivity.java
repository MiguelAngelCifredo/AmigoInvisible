package etsii.cm.amigoinvisible;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //public final String servidor = "http://192.168.1.200";
    public final String servidor = "http://asd.hol.es";

    public ArrayList<String> datos = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btListarOnClick(View view) {
        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
                datos = obtenerDatos();
                runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                mostrarListado();
                            }
                        }
                );
            }
        });
        tr.start();
    }

    public void mostrarListado(){
            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datos);
            ListView lista = (ListView) findViewById(R.id.lvListado);
            lista.setAdapter(adaptador);
    };

    public ArrayList<String> obtenerDatos(){
        ArrayList<String> lst = new ArrayList<String>();
        try {
            URL url = new URL(servidor + "/GetData.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            String response = org.apache.commons.io.IOUtils.toString(new BufferedInputStream(conn.getInputStream()), "UTF-8");
            lst = objDatosJSON(response);
        } catch (Exception e) {
            System.out.println("\nFALLO GORDO: " + e.toString());
        }
        return lst;
    }

    public ArrayList<String> objDatosJSON(String response) {
        ArrayList<String> listado = new ArrayList<String>();
        try{
            JSONArray json = new JSONArray(response);
            String texto="";
            for (int j=0;j<10;j++) {
                for (int i = 0; i < json.length(); i++) {
                    texto = json.getJSONObject(i).getString("apellidos") + ", " + json.getJSONObject(i).getString("nombre");
                    listado.add(texto);
                }
            }
        } catch(Exception e){}
        return listado;
    }

}