package dbms;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class connSrv {

    public static final String servidor = "http://192.168.1.200/amigo/";
    //public static final String servidor = "http://asd.hol.es/amigo/";

    private static String readResponse(String pagePHP){
        String response = null;
        try {
            URL url = new URL(servidor + pagePHP);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            response = org.apache.commons.io.IOUtils.toString(new BufferedInputStream(conn.getInputStream()), "UTF-8");
        } catch (Exception e) {
            System.out.println("\n***** FALLO CON LA BASE DE DATOS");
            System.out.println("\n***** PETICION ---> " + servidor + pagePHP);
            System.out.println("\n***** ERROR    ---> " + e.toString());
        }
        return response;
    }

    public static void writeData(String pagePHP) {
        readResponse(pagePHP);
    }

    public static JSONArray readJSON(String pagePHP){
        JSONArray arr = null;
        try {
            String response = readResponse(pagePHP);
            arr = new JSONArray(response);
        } catch (Exception e) {;}
        return arr;
    }

    public static Bitmap readBlob(String pagePHP) {
        Bitmap foto = null;
        try {
            String response = readResponse(pagePHP);
            byte[] arr = Base64.decode(response, Base64.DEFAULT);
            foto = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        } catch (Exception e) {;}
        return foto;
    }

}