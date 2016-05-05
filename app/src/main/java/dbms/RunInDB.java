package dbms;

import android.graphics.Bitmap;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import model.ClsEvent;
import model.ClsMyFriend;
import model.ClsPerson;
import model.ClsParticipant;
import model.ClsWish;

import utils.Iam;
import utils.Photo;

public class RunInDB {

    public ArrayList<ClsEvent> getListEvents() {
        ArrayList<ClsEvent> lst = new ArrayList<>();
        try{
            JSONArray json =  ConnSrv.readJSON("getListEvents.php?id_person=" + Iam.getId());
            for (int i=0; i<json.length(); i++) {
                lst.add(new ClsEvent(
                          json.getJSONObject(i).getInt("id_event")
                        , json.getJSONObject(i).getString("name")
                        , json.getJSONObject(i).getString("date")
                        , json.getJSONObject(i).getString("place")
                        , json.getJSONObject(i).getInt("max_price")
                        , getPhoto("event", json.getJSONObject(i).getInt("id_event"))
                        , json.getJSONObject(i).getInt("id_admin")
                ));
            }
        } catch(Exception e){;}
        return lst;
    }

    public ArrayList<ClsParticipant> getListParticipants(Integer id_event) {
    ArrayList<ClsParticipant> lst = new ArrayList<>();
    try{
        JSONArray json =  ConnSrv.readJSON("getListParticipants.php?id_event=" + id_event);
        for (int i=0; i<json.length(); i++) {
            lst.add(new ClsParticipant(
                      json.getJSONObject(i).getInt("id_participant")
                    , new ClsPerson(  json.getJSONObject(i).getInt("id_person")
                                    , json.getJSONObject(i).getString("email")
                                    , json.getJSONObject(i).getString("name")
                                    , getPhoto("person", json.getJSONObject(i).getInt("id_person"))
                                    )
                    , json.getJSONObject(i).getInt("friend")
            ));
        }
    } catch(Exception e){;}
    return lst;
    }

    public ClsPerson getPerson(Integer id_person) {
        ClsPerson person = null;
        try{
            JSONArray json =  ConnSrv.readJSON("getPerson.php?id_person=" + id_person);
            person = new ClsPerson(
                          id_person
                        , json.getJSONObject(0).getString("email")
                        , json.getJSONObject(0).getString("name")
                        , getPhoto("person", id_person)
            );
        } catch(Exception e){;}
        return person;
    }

    public ClsMyFriend getMyFriend(Integer id_event) {
        Integer id_myFriend  = null;
        JSONArray json = ConnSrv.readJSON("getMyFriend.php?id_event=" + id_event + "&id_person=" + Iam.getId());
        try{ id_myFriend = json.getJSONObject(0).getInt("friend"); } catch (Exception e) {;}
        ClsMyFriend myFriend = new ClsMyFriend( getPerson(id_myFriend) ,getListWishes(id_myFriend) );
        return myFriend;
    }

    public ArrayList<ClsWish> getListWishes(Integer id_person) {
        ArrayList<ClsWish> lst = new ArrayList<>();
        try{
            JSONArray json =  ConnSrv.readJSON("getListWishes.php?id_person=" + id_person);
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

    public ClsWish getWish(Integer id_wish) {
        ClsWish wish = null;
        try{
            JSONArray json =  ConnSrv.readJSON("getWish.php?id_wish=" + id_wish);
            wish = new ClsWish(
                          id_wish
                        , json.getJSONObject(0).getString("text")
                        , json.getJSONObject(0).getString("description")
                        , getPhoto("wish", id_wish)
                        , json.getJSONObject(0).getString("bought")
                );
        } catch(Exception e){;}
        return wish;
    }

    public Bitmap getPhoto(String source, Integer id) {
        if (!Arrays.asList(new String[]{"event","person","wish"}).contains(source)) {return null;}
        String queryURL = "getPhoto.php?source=" + source + "&id=" + id;
        Bitmap foto = ConnSrv.readBlob(queryURL);
        return foto;
    }

    public void setWishBought(Integer id_wish, String bought){
        String queryURL = "setWishBought.php?id_wish=" + id_wish + "&bought=" + bought;
        ConnSrv.writeData(queryURL);
    }

    public void setWish(ClsWish wish){
        String pagePHP = "setWish.php";

        HashMap<String, String> params = new HashMap<>();
        params.put("id_wish", wish.getData_id_wish().toString());
        params.put("text", wish.getData_text());
        params.put("description", wish.getData_description());
        params.put("photo", Photo.readFile(wish.getData_file_path()));

        ConnSrv.writePOST(pagePHP, params);
    }

    public void setEvent(ClsEvent event){
        String pagePHP = "setEvent.php";

        HashMap<String, String> params = new HashMap<>();
        params.put("id_event", event.getData_id_event().toString());
        params.put("name", event.getData_name());
        params.put("date", event.getData_date());
        params.put("place", event.getData_place());
        params.put("max_price", event.getData_max_price().toString());
        params.put("photo", Photo.readFile(event.getData_file_path()));

        ConnSrv.writePOST(pagePHP, params);
    }

    public void insPerson(ClsPerson person){
        String pagePHP = "insPerson.php";

        HashMap<String, String> params = new HashMap<>();
        params.put("email", person.getData_email());
        params.put("name", person.getData_name());
        params.put("photo", "0xnull");

        ConnSrv.writePOST(pagePHP, params);
    }

    public void insParticipant(ClsEvent event, Integer id_person){
        String pagePHP = "insParticipant.php";

        HashMap<String, String> params = new HashMap<>();
        params.put("id_event", event.getData_id_event().toString());
        params.put("id_person", id_person.toString());

        ConnSrv.writePOST(pagePHP, params);
    }

    public void insEvent(ClsEvent event){
        String pagePHP = "insEvent.php";

        HashMap<String, String> params = new HashMap<>();
        params.put("name", event.getData_name());
        params.put("date", event.getData_date());
        params.put("place", event.getData_place());
        params.put("max_price", event.getData_max_price().toString());
        params.put("id_admin", event.getData_id_admin().toString());
        params.put("photo", Photo.readFile(event.getData_file_path()));

        ConnSrv.writePOST(pagePHP, params);
    }

    public void delWish(Integer id_wish) {
        String queryURL = "delWish.php?id_wish=" + id_wish;
        ConnSrv.writeData(queryURL);
    }

    public void delParticipant(Integer id_participant) {
        String queryURL = "delParticipant.php?id_participant=" + id_participant;
        ConnSrv.writeData(queryURL);
    }

    public void delEvent(ClsEvent event) {
        String queryURL = "delEvent.php?id_event=" + event.getData_id_event();
        ConnSrv.writeData(queryURL);
    }

    public void insWish(ClsWish wish, Integer id_person){
        String pagePHP = "insWish.php";

        HashMap<String, String> params = new HashMap<>();
        params.put("id_person", id_person.toString());
        params.put("text", wish.getData_text());
        params.put("description", wish.getData_description());
        params.put("photo", Photo.readFile(wish.getData_file_path()));

        ConnSrv.writePOST(pagePHP, params);
    }

    public void setPerson(ClsPerson person){
        String pagePHP = "setPerson.php";

        HashMap<String, String> params = new HashMap<>();
        params.put("id_person", person.getData_id_person().toString());
        params.put("name", person.getData_name());
        params.put("email", person.getData_email());
        params.put("photo", Photo.readFile(person.getData_file_path()));

        ConnSrv.writePOST(pagePHP, params);
    }

    public Integer getPersonIdByEmail(String eMail){
        Integer id_person = 0;
        JSONArray json =  ConnSrv.readJSON("getPersonIdByEmail.php?email=" + eMail);
        try {
            id_person = Integer.parseInt(json.getJSONObject(0).getString("id_person"));
        }catch(Exception e){;}
        return id_person;
    }

    public Integer cntParticipant(ClsEvent event, Integer id_person){
        Integer total = 0;
        JSONArray json =  ConnSrv.readJSON("cntParticipant.php?id_event=" + event.getData_id_event() + "&id_person=" + id_person);
        try {
            total = Integer.parseInt(json.getJSONObject(0).getString("total"));
        }catch(Exception e){;}
        return total;
    }

    public Integer cntEvent(){
        Integer total = 0;
        JSONArray json =  ConnSrv.readJSON("cntEvent.php");
        try {
            total = Integer.parseInt(json.getJSONObject(0).getString("total"));
        }catch(Exception e){;}
        return total;
    }

    public void matchParticipants(ArrayList<ClsParticipant>lstParticipants){
        String pagePHP = "setFriend.php";

        for (ClsParticipant participant : lstParticipants){
            HashMap<String, String> params = new HashMap<>();
            params.put("id_participant", participant.getData_id_participant().toString());
            params.put("friend", participant.getData_friend().toString());
            ConnSrv.writePOST(pagePHP, params);
        }
    }
}