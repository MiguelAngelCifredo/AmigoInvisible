package utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.Collections;
import comparator.PersonName;
import model.ClsPerson;

public class Contactos {

    public static ArrayList<ClsPerson> listContacts;

    public static void findContacts(Activity activity){

        ArrayList<ClsPerson> res = new ArrayList<>();

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
                    String name  = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String email = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));

                    byte[] array = cur1.getBlob(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO));
                    Bitmap photo = null;
                    if (array != null) {
                        photo = BitmapFactory.decodeByteArray(array, 0, array.length);
                    }
                    if(email!=null && email.contains("@gmail.com")){
                        res.add(new ClsPerson(0, email, name, photo));
                    }
                }
                cur1.close();
            }
        }

        Collections.sort(res, new PersonName());

        listContacts = res;
    }

}
