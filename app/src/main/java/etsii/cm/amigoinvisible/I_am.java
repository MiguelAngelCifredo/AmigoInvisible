package etsii.cm.amigoinvisible;

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

}
