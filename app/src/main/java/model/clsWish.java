package model;

public class ClsWish {
    private Integer data_id_wish;
    private String  data_text;
    private String  data_description;
    private String  data_photo;
    private String  data_bouth;

    public ClsWish( Integer data_id_wish
                  , String  data_text
                  , String  data_description
                  , String  data_photo
                  , String  data_bouth
    ) {
        this.data_id_wish     = data_id_wish;
        this.data_text        = data_text;
        this.data_description = data_description;
        this.data_photo       = data_photo;
        this.data_bouth       = data_bouth;
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

    public String getData_photo() {
        return data_photo;
    }

    public void setData_photo(String data_photo) {
        this.data_photo = data_photo;
    }

    public String getData_bouth() {
        return data_bouth;
    }

    public void setData_bouth(String data_bouth) {
        this.data_bouth = data_bouth;
    }
}
