package dbms;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import model.ClsEvent;
import model.ClsMyFriend;
import model.ClsPerson;
import model.ClsParticipant;
import model.ClsWish;

public class getInfo {

    public ArrayList<ClsEvent> getListEvents() {
        ArrayList<ClsEvent> lst = new ArrayList<>();
        try{
            JSONArray json =  connServer.readData("getListEvents.php");
            for (int i=0; i<json.length(); i++) {
                lst.add(new ClsEvent(
                          json.getJSONObject(i).getInt("id_event")
                        , json.getJSONObject(i).getString("name")
                        , json.getJSONObject(i).getString("date")
                        , json.getJSONObject(i).getString("place")
                        , json.getJSONObject(i).getInt("max_price")
                ));
            }
        } catch(Exception e){}
        return lst;
    }

    public ArrayList<ClsParticipant> getListParticipants(Integer id_event) {
        ArrayList<ClsParticipant> lst = new ArrayList<>();
        try{
            JSONArray json =  connServer.readData("getListParticipants.php?id_event=" + id_event);
            for (int i=0; i<json.length(); i++) {
                lst.add(new ClsParticipant(
                          json.getJSONObject(i).getInt("id_participant")
                        , getPerson(json.getJSONObject(i).getInt("id_person"))
                        , json.getJSONObject(i).getString("admin")
                        , json.getJSONObject(i).getInt("friend")
                ));
            }
        } catch(Exception e){}
        return lst;
    }

    public ClsPerson getPerson(Integer id_person) {
        ClsPerson person = null;
        try{
            JSONArray json =  connServer.readData("getPerson.php?id_person=" + id_person);
            person = new ClsPerson(
                          id_person
                        , json.getJSONObject(0).getString("email")
                        , json.getJSONObject(0).getString("name")
                        , null
            );
        } catch(Exception e){}
        return person;
    }

    public ClsMyFriend getMyFriend(Integer id_person) {
        ClsMyFriend myFriend = null;
        try{
            myFriend = new ClsMyFriend(
                     getPerson(id_person)
                    ,getListWishes(id_person)
            );
        } catch(Exception e){}
        return myFriend;
    }

    public ArrayList<ClsWish> getListWishes(Integer id_person) {
        ArrayList<ClsWish> lst = new ArrayList<>();
        try{
            JSONArray json =  connServer.readData("getListWishes.php?id_person=" + id_person);
            for (int i=0; i<json.length(); i++) {
                lst.add(new ClsWish(
                          json.getJSONObject(i).getInt("id_wish")
                        , json.getJSONObject(i).getString("text")
                        , json.getJSONObject(i).getString("description")
                        , null
                        , json.getJSONObject(i).getString("bought")
                ));
            }
        } catch(Exception e){}
        return lst;
    }

    public String getPersonPhoto() {
        String queryURL = "/amigo/getPersonPhoto.php?id_person=1";
        String response = null;
        try {
            URL url = new URL(connServer.servidor + queryURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            response = org.apache.commons.io.IOUtils.toString(new BufferedInputStream(conn.getInputStream()), "UTF-8");
        } catch (Exception e) {}
        return response;
    }

}
