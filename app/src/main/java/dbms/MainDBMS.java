package dbms;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainDBMS {

    //private final String servidor = "http://192.168.1.200";
    public final String servidor = "http://asd.hol.es";

    private JSONArray readData(String pagePHP){
        String queryURL = "/amigo/" + pagePHP;
        JSONArray arr = null;
        try {
            URL url = new URL(servidor + queryURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            String response = org.apache.commons.io.IOUtils.toString(new BufferedInputStream(conn.getInputStream()), "UTF-8");
            arr = new JSONArray(response);
        } catch (Exception e) {
            System.out.println("\nFALLO DESDE LA BASE DE DATOS ---> " + e.toString());
        }
        return arr;
    }

    public ArrayList<clsEvent> getEvents() {
        ArrayList<clsEvent> lst = new ArrayList<>();
        try{
            JSONArray json =  readData("getEvents.php");
            for (int i=0; i<json.length(); i++) {
                lst.add(new clsEvent( json.getJSONObject(i).getInt("id_event")
                                    , json.getJSONObject(i).getString("name")
                                    , json.getJSONObject(i).getString("date")
                                    , json.getJSONObject(i).getString("place")
                                    , json.getJSONObject(i).getInt("max_price")
                ));
            }
        } catch(Exception e){}
        return lst;
    }

    public ArrayList<clsPerson> getPersons() {
        ArrayList<clsPerson> lst = new ArrayList<>();
        try{
            JSONArray json =  readData("getPersons.php");
            for (int i=0; i<json.length(); i++) {
                lst.add(new clsPerson( json.getJSONObject(i).getInt("id_person")
                                     , json.getJSONObject(i).getString("email")
                                     , json.getJSONObject(i).getString("name")
                                     , null
                ));
/*
                Mientras consigo la forma de obtener la foto,
                sólo se obtendrán el resto de campos.  :-(

                lst.add(new clsPerson( json.getJSONObject(i).getInt("id_person")
                                     , json.getJSONObject(i).getString("email")
                                     , json.getJSONObject(i).getString("name")
                                     , json.getJSONObject(i).getString("photo")
                ));

 */
            }
        } catch(Exception e){}
        return lst;
    }

    public String getPersonPhoto() {
        String queryURL = "/amigo/getPersonPhoto.php?id_person=1";
        String response = null;
        try {
            URL url = new URL(servidor + queryURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            response = org.apache.commons.io.IOUtils.toString(new BufferedInputStream(conn.getInputStream()), "UTF-8");
        } catch (Exception e) {}
        return response;
    }

}
