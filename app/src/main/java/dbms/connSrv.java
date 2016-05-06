package dbms;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

public class ConnSrv {

    //public static final String servidor = "http://192.168.1.200/amigo/";
    public static final String servidor = "http://asd.hol.es/amigo/";

    private static final boolean log = true;

    private static String sendPage(String pagePHP){
        if (log) System.out.println("\n***** PETICION BD ---> " + servidor + pagePHP);
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
        sendPage(pagePHP);
    }

    public static JSONArray readJSON(String pagePHP){
        JSONArray arr = null;
        try {
            String response = sendPage(pagePHP);
            arr = new JSONArray(response);
        } catch (Exception e) {;}
        return arr;
    }

    public static Bitmap readBlob(String pagePHP) {
        Bitmap foto = null;
        try {
            String response = sendPage(pagePHP);
            byte[] arr = Base64.decode(response, Base64.DEFAULT);
            foto = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        } catch (Exception e) {;}
        return foto;
    }

    public static void writePOST(String pagePHP, HashMap<String, String> params){
        HttpURLConnection conn = null;
        String charset = "UTF-8";
        StringBuilder sbParams = new StringBuilder();
        int i = 0;
        for (String key : params.keySet()) {
            try {
                if (i != 0){ sbParams.append("&"); }
                sbParams.append(key).append("=").append(URLEncoder.encode(params.get(key), charset));
            } catch (UnsupportedEncodingException e) {
                System.out.println("****** ERROR writePOST parsing params -> " + e.getMessage());
            }
            i++;
        }
        // Envío hacia el Server
        try {
            URL url = new URL(servidor + pagePHP);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept-Charset", charset);
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.connect();

            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(sbParams.toString());
            wr.flush();
            wr.close();
        } catch (IOException e) {
            System.out.println("****** ERROR writePOST SENDING -> " + e.getMessage());
        }
        //if (log){
            // Lectura de alguna respuesta desde el Server
            try {
                InputStream in = new BufferedInputStream(conn.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) { result.append(line); }
                if (log) System.out.println("****** RESPUESTA despues de WritePOST : " + result.toString());
            } catch (IOException e) {
                System.out.println("****** ERROR writePOST RECEIVING  -> " + e.getMessage());
            }
        //}

        conn.disconnect();
    }
}