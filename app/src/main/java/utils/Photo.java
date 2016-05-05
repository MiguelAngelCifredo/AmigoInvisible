package utils;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileInputStream;

public class Photo {

    public static String readFile(String filename) {
        String byte2Hex = null;

        if (filename != null) {
            System.out.println("****** Fch:" + filename);
            try {
                File a_file = new File(filename);
                byte[] buffer = null;
                FileInputStream fis = new FileInputStream(filename);
                int length = (int) a_file.length();
                buffer = new byte[length];
                fis.read(buffer);
                fis.close();
                byte2Hex = bytesToHex(buffer);
            } catch (Exception e) {
                System.out.println("****** FALLO =  Fch:" + filename + " -> " + e.getMessage());
            }
        }

        return "0x" + byte2Hex;
    }

    private static String bytesToHex(byte[] bytes) {
        final char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String getPath(Uri contentUri, final Activity activity) {
        String res = null;
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = activity.getContentResolver().query(contentUri, projection, null, null, null);
        if(cursor.moveToFirst()){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        if (cursor != null) {
            cursor.close();
        }
        return res;
    }

}
