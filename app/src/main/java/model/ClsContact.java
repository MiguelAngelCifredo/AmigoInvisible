package model;

import android.graphics.Bitmap;

/**
 * Created by alberto on 26/04/2016.
 */

public class ClsContact {

    // atributos

    private String name;
    private String email;
    private Bitmap photo;

    // constructor
    public ClsContact(String name, String email, Bitmap photo) {
        this.name = name;
        this.email = email;
        this.photo = photo;
    }

    // getters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    //setters
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClsContact)) return false;

        ClsContact that = (ClsContact) o;

        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null)
            return false;
        return getEmail().equals(that.getEmail());
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + getEmail().hashCode();
        return result;
    }
}

