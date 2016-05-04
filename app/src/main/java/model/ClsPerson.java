package model;

import android.graphics.Bitmap;

public class ClsPerson {

    private Integer data_id_person;
    private String  data_email;
    private String  data_name;
    private Bitmap  data_photo;
    private String  data_file_path;

    public ClsPerson( Integer data_id_person
                    , String  data_email
                    , String  data_name
                    , Bitmap  data_photo
    ){
        this.data_id_person = data_id_person;
        this.data_email     = data_email;
        this.data_name      = data_name;
        this.data_photo     = data_photo;
    }

    public Integer getData_id_person() {
        return data_id_person;
    }

    public void setData_id_person(Integer data_id_person) {
        this.data_id_person = data_id_person;
    }

    public String getData_email() {
        return data_email;
    }

    public void setData_email(String data_email) {
        this.data_email = data_email;
    }

    public String getData_name() {
        return data_name;
    }

    public void setData_name(String data_name) {
        this.data_name = data_name;
    }

    public Bitmap getData_photo() {
        return data_photo;
    }

    public void setData_photo(Bitmap data_photo) {
        this.data_photo = data_photo;
    }

    public String getData_file_path() {
        return data_file_path;
    }

    public void setData_file_path(String data_file_path) {
        this.data_file_path = data_file_path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClsPerson)) return false;

        ClsPerson that = (ClsPerson) o;

        if (getData_name() != null ? !getData_name().equals(that.getData_name()) : that.getData_name() != null)
            return false;
        return getData_email().equals(that.getData_email());
    }

    @Override
    public int hashCode() {
        int result = getData_name() != null ? getData_name().hashCode() : 0;
        result = 31 * result + getData_email().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ClsPerson{" +
                "id_person=" + data_id_person +
                ", email='" + data_email + '\'' +
                ", name='" + data_name + '\'' +
                ", photo='" + data_photo + '\'' +
                '}';
    }
}