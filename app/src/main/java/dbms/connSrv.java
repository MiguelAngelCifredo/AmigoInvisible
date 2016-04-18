package dbms;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import model.ClsWish;

public class ConnSrv {

    public static final String servidor = "http://192.168.1.200/amigo/";
    //public static final String servidor = "http://asd.hol.es/amigo/";

    private static String readPage(String pagePHP){
        System.out.println("\n***** PETICION ---> " + servidor + pagePHP);
        String response = null;
        try {
            URL url = new URL(servidor + pagePHP);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            response = org.apache.commons.io.IOUtils.toString(new BufferedInputStream(conn.getInputStream()), "UTF-8");
        } catch (Exception e) {
            System.out.println("\n***** FALLO CON LA BASE DE DATOS");
            System.out.println("\n***** ERROR    ---> " + e.toString());
        }
        return response;
    }

    public static void writeData(String pagePHP) {
        readPage(pagePHP);
    }

    public static void writePOST(String pagePHP, String parameters){
            try {
                URL url = new URL(servidor + pagePHP);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("User-Agent", "Mozilla/5.0");
                conn.setDoOutput(true);
                OutputStream os = conn.getOutputStream();
                os.write(parameters.getBytes());
                os.flush();
                os.close();

                int responseCode = conn.getResponseCode();
                System.out.println("********** POST StatusCode: " + responseCode);
                if (responseCode == HttpURLConnection.HTTP_OK) { //success
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    System.out.println("********** POST Response: " + response.toString());
                } else {
                    System.out.println("********** POST request not worked");
                }
            } catch (Exception e) {
                System.out.println("\n***** FALLO CON EL POST");
                System.out.println("\n***** ERROR    ---> " + e.toString());
            }
    }

    public static JSONArray readJSON(String pagePHP){
        JSONArray arr = null;
        try {
            String response = readPage(pagePHP);
            arr = new JSONArray(response);
        } catch (Exception e) {;}
        return arr;
    }

    public static Bitmap readBlob(String pagePHP) {
        Bitmap foto = null;
        try {
            String response = readPage(pagePHP);
            byte[] arr = Base64.decode(response, Base64.DEFAULT);
            foto = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        } catch (Exception e) {;}
        return foto;
    }

}