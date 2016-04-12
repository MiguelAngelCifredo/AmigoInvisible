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

    public ArrayList<ClsEvent> getListEvents() {
        ArrayList<ClsEvent> lst = new ArrayList<>();
        try{
            JSONArray json =  connSrv.readJSON("getListEvents.php");
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
            JSONArray json =  connSrv.readJSON("getListParticipants.php?id_event=" + id_event);
            for (int i=0; i<json.length(); i++) {
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
            JSONArray json =  connSrv.readJSON("getPerson.php?id_person=" + id_person);
            person = new ClsPerson(
                          id_person
                        , json.getJSONObject(0).getString("email")
                        , json.getJSONObject(0).getString("name")
                        , getPhoto("person", id_person)
            );
        } catch(Exception e){;}
        return person;
    }

    public ClsMyFriend getMyFriend(Integer id_event, String email) {
        Integer id_myFriend  = null;
        JSONArray json = connSrv.readJSON("getMyFriend.php?id_event=" + id_event + "&email=" + email);
        try{ id_myFriend = json.getJSONObject(0).getInt("friend"); } catch (Exception e) {;}
        ClsMyFriend myFriend = new ClsMyFriend( getPerson(id_myFriend) ,getListWishes(id_myFriend) );
        return myFriend;
    }

    public ArrayList<ClsWish> getListWishes(Integer id_person) {
        ArrayList<ClsWish> lst = new ArrayList<>();
        try{
            JSONArray json =  connSrv.readJSON("getListWishes.php?id_person=" + id_person);
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
        String queryURL = "getPhoto.php?source=" + source + "&id=" + id;
        Bitmap foto = connSrv.readBlob(queryURL);
        return foto;
    }

    public void setWishBought(Integer id_wish, String bought){
        String queryURL = "setWishBought.php?id_wish=" + id_wish + "&bought=" + bought;
        connSrv.writeData(queryURL);
    }
}
