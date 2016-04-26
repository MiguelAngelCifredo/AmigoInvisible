package utils;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import model.ClsContact;

/**
 * Created by alberto on 26/04/2016.
 */
public class ContactosTerminal {
    public ArrayList<ClsContact> getNameEmailDetails(Activity activity){

        ArrayList<ClsContact> res = new ArrayList<ClsContact>();

        ArrayList<String> names = new ArrayList<String>();
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
                    //to get the contact names
                    String name=cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    //Log.e("Name :", name);
                    String email = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    //Log.e("Email", email);
                    byte[] array = cur1.getBlob(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO));
                    Bitmap photo = BitmapFactory.decodeByteArray(array,0,array.length);

                    if(email!=null){
                        names.add(name + "@@@ " + email);
                        res.add(new ClsContact(name, email, photo));
                    }
                }
                cur1.close();
            }
        }
        return res;
    }// end getNameEmailDetails


    //private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;


    /*
    private void showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            List<String> contacts = getNameEmailDetails();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contacts);
            lstNames.setAdapter(adapter);
        }
    }
    */

    //@Override
    /*
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showContacts();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    } //end onRequestPermissionsResult

    */

    /**
     * Read the name of all the contacts.
     *
     * @return a list of names.
     */
    /*
    private List<String> getContactNames() {
        List<String> contacts = new ArrayList<>();
        // Get the ContentResolver
        ContentResolver cr = getContentResolver();
        // Get the Cursor of all the contacts
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
        Cursor cur1 = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, null);

        // Move the cur to first. Also check whether the cur is empty or not.
        if (cur.moveToFirst()) {
            // Iterate through the cur
            do {
                // Get the contacts name
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))+ " @@@ " + cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                contacts.add(name);
            } while (cur.moveToNext());
        }
        // Close the curosor
        cur.close();

        return contacts;
    } // end getContactNames

*/



}
