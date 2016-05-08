package utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.Collections;
import comparator.PersonName;
import model.ClsPerson;

public class Contactos {

    public static ArrayList<ClsPerson> listContacts;

    public static ArrayList<ClsPerson> getContacts(Activity activity){
        /*
        obtiene (previo permiso del usuario) los siguientes datos de cada contacto
        almacenado en el terminal:
        nombre, email, foto
         */

        ArrayList<ClsPerson> res = new ArrayList<>();
        Bitmap photo = null;

        ContentResolver cr = activity.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                Cursor cur1 = cr.query(
                        ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                        new String[]{id}, null);
                while (cur1.moveToNext()) {
                    //to get the contact names, emails and photos
                    String name      = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String email     = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    String image_uri = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));

                    // bitmap de la imagen
                    if (image_uri != null) {
                        try {
                            photo = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), Uri.parse(image_uri));
                        } catch (Exception e) {;}
                    }
                    else photo = null;

                    if(email!=null && email.contains("@gmail.com")){
                        res.add(new ClsPerson(0, email, name, photo));
                    }
                }
                cur1.close();
            }
        }

        Collections.sort(res, new PersonName());
        listContacts = res;

        return res;
    }

}
