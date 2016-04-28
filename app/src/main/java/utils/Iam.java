package utils;

import android.Manifest;
import android.accounts.Account;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import model.ClsEvent;

public class Iam {
    private static Integer id_person = null;
    private static String eMail = null;

    public static void setData(Integer new_id_person, String new_eMail) {
        id_person = new_id_person;
        eMail = new_eMail;
    }

    public static Integer getId() {
        return id_person;
    }

    public static String getEmail() {
        return eMail;
    }

    public static void setId(Integer new_id_person) {
        id_person = new_id_person;
    }

    public static void setEmail(String new_eMail) {
        eMail = new_eMail;
    }

    public static String gMailAccount(Activity activity){
        final int VALOR_PARA_DISTINGUIR_LA_PETICION = 66; // VALOR_PARA_DISTINGUIR_LA_PETICION = 66 identifica la petición de permisos
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.GET_ACCOUNTS)!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.GET_ACCOUNTS)) {
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.GET_ACCOUNTS}, VALOR_PARA_DISTINGUIR_LA_PETICION);
            }
        } else {
            android.accounts.AccountManager am = android.accounts.AccountManager.get(activity);
            android.accounts.Account[] accounts = am.getAccounts();
            for(Account acc : accounts) {
                if(acc.type.matches("com.google")) eMail = acc.name.toString();
            }
        }
        return eMail;
    }

    public static boolean admin(ClsEvent eventActual){
        return eventActual.getData_id_admin() == Iam.getId();
    }

}