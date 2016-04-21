package dbms;

import org.json.JSONException;
import org.json.JSONObject;

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

public class JSONParser {

    public static JSONObject makeHttpRequest(String url, String method, HashMap<String, String> params) {

        String charset = "UTF-8";
        HttpURLConnection conn = null;
        DataOutputStream wr;
        StringBuilder result = new StringBuilder();
        URL urlObj;
        JSONObject jObj = null;
        StringBuilder sbParams;
        String paramsString;
        /*

        JSONParser jsonParser = new JSONParser();
        JSONObject json = jsonParser.makeHttpRequest(pagePHP, "POST", params);

         */

        sbParams = new StringBuilder();
        int i = 0;
        for (String key : params.keySet()) {
            try {
                if (i != 0){
                    sbParams.append("&");
                }
                sbParams.append(key).append("=").append(URLEncoder.encode(params.get(key), charset));
            } catch (UnsupportedEncodingException e) {
                System.out.println("****** JSON ERROR 1 -> " + e.getMessage());
            }
            i++;
        }

        if (method.equals("POST")) {
            // request method is POST
            try {
                urlObj = new URL(url);
                conn = (HttpURLConnection) urlObj.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Accept-Charset", charset);
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.connect();

                paramsString = sbParams.toString();

                wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(paramsString);
                wr.flush();
                wr.close();

            } catch (IOException e) {
                System.out.println("****** JSON ERROR 2 -> " + e.getMessage());
            }
        }
        else if(method.equals("GET")){
            // request method is GET

            if (sbParams.length() != 0) {
                url += "?" + sbParams.toString();
            }

            try {
                urlObj = new URL(url);
                conn = (HttpURLConnection) urlObj.openConnection();
                conn.setDoOutput(false);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept-Charset", charset);
                conn.setConnectTimeout(15000);
                conn.connect();

            } catch (IOException e) {
                System.out.println("****** JSON ERROR 3 -> " + e.getMessage());
            }
        }

        try {
            //Receive the response from the server
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            System.out.println("****** JSON OK respuesta  -> " + result.toString());

        } catch (IOException e) {
            System.out.println("****** JSON ERROR 4 -> " + e.getMessage());
        }

        conn.disconnect();

        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(result.toString());
        } catch (JSONException e) {
            System.out.println("****** JSON ERROR 5 -> " + e.getMessage());
        }

        // return JSON Object
        return jObj;
    }
}