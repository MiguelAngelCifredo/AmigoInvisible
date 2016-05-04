package model;

import android.graphics.Bitmap;

public class ClsWish {

    private Integer data_id_wish;
    private String  data_text;
    private String  data_description;
    private Bitmap  data_photo;
    private String  data_bought;
    private String  data_file_path;

    public ClsWish( Integer data_id_wish
                  , String  data_text
                  , String  data_description
                  , Bitmap  data_photo
                  , String data_bought
    ) {
        this.data_id_wish     = data_id_wish;
        this.data_text        = data_text;
        this.data_description = data_description;
        this.data_photo       = data_photo;
        this.data_bought      = data_bought;
    }

    public Integer getData_id_wish() {
        return data_id_wish;
    }

    public void setData_id_wish(Integer data_id_wish) {
        this.data_id_wish = data_id_wish;
    }

    public String getData_text() {
        return data_text;
    }

    public void setData_text(String data_text) {
        this.data_text = data_text;
    }

    public String getData_description() {
        return data_description;
    }

    public void setData_description(String data_description) {
        this.data_description = data_description;
    }

    public Bitmap getData_photo() {
        return data_photo;
    }

    public void setData_photo(Bitmap data_photo) {
        this.data_photo = data_photo;
    }

    public String getData_bought() {
        return data_bought;
    }

    public void setData_bought(String data_bought) {
        this.data_bought = data_bought;
    }

    public String getData_file_path() {
        return data_file_path;
    }

    public void setData_file_path(String data_file_path) {
        this.data_file_path = data_file_path;
    }

    @Override
    public String toString() {
        return "ClsWish{" +
                "id_wish=" + data_id_wish +
                ", text='" + data_text + '\'' +
                ", description='" + data_description + '\'' +
                ", bougth='" + data_bought + '\'' +
                '}';
    }

}
