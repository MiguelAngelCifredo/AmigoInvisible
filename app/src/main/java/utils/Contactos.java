package utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;

import java.io.FileNotFoundException;
import java.io.IOException;
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
    // FUNCIONA PERFECTAMENTE
    public static void readContacts(Activity activity) {
        ArrayList<ClsPerson> res = new ArrayList<>();

        ContentResolver cr = activity.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        String phone = null;
        String emailContact = "";
        String emailType = null;
        String image_uri = "";
        Bitmap bitmap = null;
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                image_uri = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));

                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[] { id }, null);
                    while (pCur.moveToNext()) {
                        phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    }
                    pCur.close();

                    Cursor emailCur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[] { id }, null);
                    while (emailCur.moveToNext()) {
                        emailContact = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        emailType = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
                    }
                    emailCur.close();
                }
                // bitmap de la imagen
                if (image_uri != null) {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), Uri.parse(image_uri));
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                // else Bitmap bitmap = default contact photo
                else {
                    bitmap = null;
                }
                // solo añade si el contacto es de gmail
                if(emailContact.contains("@gmail.com")) {
                    res.add(new ClsPerson(0,name, emailContact, bitmap));
                }
            }
        }
        Collections.sort(res, new PersonName());
        listContacts = res;
    }// end readContacts()

}
