package model;

public class clsParticipant {
    private Integer   data_id_participant;
    private clsPerson data_person;
    private String    data_admin;
    private Integer   data_friend;

    public clsParticipant( Integer   data_id_participant
                         , clsPerson data_person
                         , String    data_admin
                         , Integer   data_friend
    ) {
        this.data_id_participant   = data_id_participant;
        this.data_admin     = data_admin;
        this.data_person    = data_person;
        this.data_friend    = data_friend;
    }

    public Integer getData_id_participant() {
        return data_id_participant;
    }

    public void setData_id_participant(Integer data_id_team) {
        this.data_id_participant = data_id_team;
    }

    public String getData_admin() {
        return data_admin;
    }

    public void setData_admin(String data_admin) {
        this.data_admin = data_admin;
    }

    public Integer getData_friend() {
        return data_friend;
    }

    public void setData_friend(Integer data_friend) {
        this.data_friend = data_friend;
    }

    public clsPerson getData_person() {
        return data_person;
    }

    public void setData_id_person(clsPerson data_person) {
        this.data_person = data_person;
    }
}