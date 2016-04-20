package utils;

import android.Manifest;
import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class I_am {
    private static Integer id_person = null;
    private static String eMail = null;

    public static void setData(Integer new_id_person, String new_eMail) {
        id_person = new_id_person;
        eMail     = new_eMail;
    }

    public static Integer getId() {
        return id_person;
    }

    public static String getEmail() {
        return eMail;
    }



    public static String getGmailAccount(Activity act){
        String possibleEmail = "";
        // VALOR_PARA_DISTINGUIR_LA_PETICION = 66 identifica la petición de permisos

        final int VALOR_PARA_DISTINGUIR_LA_PETICION = 66;
        // possibleEmail obtiene el email del terminal
        // Acceso a la cuenta de email principal del terminal
        // la variable será possibleEmail
        if (ContextCompat.checkSelfPermission(act, Manifest.permission.GET_ACCOUNTS)!= PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(act, Manifest.permission.GET_ACCOUNTS)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(act, new String[]{Manifest.permission.GET_ACCOUNTS}, VALOR_PARA_DISTINGUIR_LA_PETICION);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            android.accounts.AccountManager am = android.accounts.AccountManager.get(act);
            android.accounts.Account[] accounts = am.getAccounts();

            for(Account acc : accounts) {
                System.out.println(acc.toString());
                //acc.name es el nombre de la cuenta primaria de google.com del terminal
                if(acc.type.matches("com.google")) possibleEmail = acc.name.toString();
                //System.out.println("@@@ posible eMail" + possibleEmail);
                //System.out.println("@@@ cuenta: " + acc.name.toString());
            }
        } // end Acceso a la cuenta de email principal del terminal
        return possibleEmail;
    }

}
