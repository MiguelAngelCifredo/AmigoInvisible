package dbms;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import model.ClsEvent;
import model.ClsMyFriend;
import model.ClsPerson;
import model.ClsParticipant;
import model.ClsWish;

public class getInfo {

    private static final String LogTAG = " MACC getInfo";

    public ArrayList<ClsEvent> getListEvents() {
        ArrayList<ClsEvent> lst = new ArrayList<>();
        try{
            JSONArray json =  connSrv.readData("getListEvents.php");
            for (int i=0; i<json.length(); i++) {
                lst.add(new ClsEvent(
                          json.getJSONObject(i).getInt("id_event")
                        , json.getJSONObject(i).getString("name")
                        , json.getJSONObject(i).getString("date")
                        , json.getJSONObject(i).getString("place")
                        , json.getJSONObject(i).getInt("max_price")
                        , getPhoto("event", json.getJSONObject(i).getInt("id_event"))
                ));
            }
        } catch(Exception e){}
        return lst;
    }

    public ArrayList<ClsParticipant> getListParticipants(Integer id_event) {
        ArrayList<ClsParticipant> lst = new ArrayList<>();
        try{
            JSONArray json =  connSrv.readData("getListParticipants.php?id_event=" + id_event);
            for (int i=0; i<json.length(); i++) {
                Log.d(LogTAG,"id person:" + json.getJSONObject(i).getInt("id_person") + " friend:" + json.getJSONObject(i).getInt("friend"));
                lst.add(new ClsParticipant(
                          json.getJSONObject(i).getInt("id_participant")
                        , getPerson(json.getJSONObject(i).getInt("id_person"))
                        , json.getJSONObject(i).getString("admin")
                        , getPerson(json.getJSONObject(i).getInt("friend"))
                ));
            }
        } catch(Exception e){;}
        return lst;
    }

    public ClsPerson getPerson(Integer id_person) {
        ClsPerson person = null;
        try{
            JSONArray json =  connSrv.readData("getPerson.php?id_person=" + id_person);
            person = new ClsPerson(
                          id_person
                        , json.getJSONObject(0).getString("email")
                        , json.getJSONObject(0).getString("name")
                        , getPhoto("person", id_person)
            );
        } catch(Exception e){;}
        return person;
    }

    public ClsMyFriend getMyFriend_Original(Integer id_event) {
        ClsMyFriend myFriend = null;
        Integer id_myFriend  = null;
        try{
            ArrayList<ClsParticipant> lst = getListParticipants(id_event);
            for(int i=0; i<lst.size(); i++){
                if (lst.get(i).getData_person().getData_email().equals("macifredo@gmail.com"))
                    id_myFriend = lst.get(i).getData_friend().getData_id_person();
            }
            myFriend = new ClsMyFriend(
                     getPerson(id_myFriend)
                    ,getListWishes(id_myFriend)
            );
        } catch(Exception e){;}
        return myFriend;
    }

    public ClsMyFriend getMyFriend(Integer id_event, String email) {
        Integer id_myFriend  = null;
        JSONArray json = connSrv.readData("getMyFriend.php?id_event=" + id_event +"&email=" + email);
        try{ id_myFriend = json.getJSONObject(0).getInt("friend"); } catch (Exception e) {;}
        ClsMyFriend myFriend = new ClsMyFriend( getPerson(id_myFriend) ,getListWishes(id_myFriend) );
        return myFriend;
    }

    public ArrayList<ClsWish> getListWishes(Integer id_person) {
        ArrayList<ClsWish> lst = new ArrayList<>();
        try{
            JSONArray json =  connSrv.readData("getListWishes.php?id_person=" + id_person);
            for (int i=0; i<json.length(); i++) {
                lst.add(new ClsWish(
                          json.getJSONObject(i).getInt("id_wish")
                        , json.getJSONObject(i).getString("text")
                        , json.getJSONObject(i).getString("description")
                        , getPhoto("wish", json.getJSONObject(i).getInt("id_wish"))
                        , json.getJSONObject(i).getString("bought")
                ));
            }
        } catch(Exception e){;}
        return lst;
    }

    public Bitmap getPhoto(String source, Integer id) {
        if (!Arrays.asList(new String[]{"event","person","wish"}).contains(source)) {return null;}
        //String servidor = "http://192.168.1.200";
        String servidor = "http://asd.hol.es";
        String queryURL = "/amigo/getPhoto.php?source=" + source + "&id=" + id;
        Bitmap foto = null;
        try {
            URL url = new URL(servidor + queryURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            String response = org.apache.commons.io.IOUtils.toString(new BufferedInputStream(conn.getInputStream()), "UTF-8");
            byte[] arr = Base64.decode(response, Base64.DEFAULT);
            foto = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        } catch (Exception e) {;}
        return foto;
    }

}
