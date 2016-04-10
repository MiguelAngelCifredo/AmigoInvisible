package model;
//--
public class ClsPerson {

    private Integer data_id_person;
    private String  data_email;
    private String  data_name;
    private String  data_photo;

    public ClsPerson( Integer data_id_person
                    , String  data_email
                    , String  data_name
                    , String  data_photo
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

    public String getData_photo() {
        return data_photo;
    }

    public void setData_photo(String data_photo) {
        this.data_photo = data_photo;
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