package utils;

import android.Manifest;
import android.accounts.Account;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;

import model.ClsEvent;
import model.ClsPerson;

public class Iam {
    private static Integer id_person = null;
    private static String  eMail     = null;
    private static String  name      = null;
    private static Bitmap  photo     = null;

    public static Integer getId() {
        return id_person;
    }

    public static String getEmail() {
        return eMail;
    }

    public static String getName() {
        return name;
    }

    public static Bitmap getPhoto() {
        return photo;
    }

    public static void setId(Integer new_id_person) {
        id_person = new_id_person;
    }

    public static void setEmail(String new_eMail) {
        eMail = new_eMail;
    }

    public static void setName(String name) {
        Iam.name = name;
    }

    public static void setPhoto(Bitmap photo) {
        Iam.photo = photo;
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
                if(acc.type.matches("com.google")) {
                    eMail = acc.name.toString();
                    /*
                    String name = acc.name;
                    System.out.println("@@@@@@@@@@@" + acc);
                    Log.i("@@@@@@@ acc:", acc.toString());
                    */

                }
            }

        }
        Log.i("@@@@@@@ email", eMail);
        return eMail;
    }

    public static boolean admin(ClsEvent eventActual){
        return eventActual.getData_id_admin() == Iam.getId();
    }
    private static void setIam(Activity activity){
        /*
        mediante el método Contactos.getContacts(Activity activity) obtenemos la lista entera de los contactos.
        Posteriormente lo comparamos con Iam.gMailAccount(Activity activity) que es nuestro email y obtenemos
        todos los datos de la clase ClsPerson.
         */

        String myEmail = Iam.gMailAccount(activity);
        Boolean existoEnDB = Iam.estoyRegistradoEnDB(myEmail);

        // Si no existo en la DB, obtiene mis datos personales del terminal y los envia a la DB.
        if (existoEnDB.equals(false)){
            ClsPerson misDatos = Iam.obtainMyData(Iam.gMailAccount(activity), Contactos.getContacts(activity));
            // modifica los datos
            Iam.eMail     = misDatos.getData_email();
            Iam.name      = misDatos.getData_name();
            Iam.photo     = misDatos.getData_photo();
            Iam.id_person = registraEnDB(Iam.name, Iam.eMail, Iam.photo);
        }
        // Si resulta que SI estoy registrado en la DB, obtengo los datos de ella.
        else{
            /*
            Iam.eMail     = myEmail;
            Iam.name      = getDBmyName(Iam.eMail);
            Iam.photo     = getDBmyPhoto(Iam.eMail);
            Iam.id_person = getDBmyID(Iam.eMail);
            */

        }


    }// end setIam()

    private static Integer registraEnDB(String name, String email, Bitmap photo) {
        /*
        registra los datos en la DB y devuelve el ID de la pkey asignado
         */

        return null;
    }

    private static Boolean estoyRegistradoEnDB(String email) {
        /*
        Devuelve true si existo en la DB o falso si no
         */
        Boolean res = false;
        return res;
    }

    private static ClsPerson obtainMyData(String miEmail, ArrayList<ClsPerson> lp){
        /*
        de la lista de contactos del movil devuelve el contacto cuyo email es el nuestro,
        si no, devuelve un ClsPerson nulo. 
         */
        
        // como res hacemos un ClsPerson nulo. 
        ClsPerson res =  new ClsPerson(-1, "", "John Doe", null);
        for(ClsPerson persona: lp){
            if(persona.getData_email().equals(miEmail)) {res = persona;}
        }
        return res;
    }// end obtainMyData()

}