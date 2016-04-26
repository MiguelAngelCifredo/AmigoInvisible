package model;

public class ClsParticipant {

    private Integer   data_id_participant;
    private ClsPerson data_person;
    private Integer   data_friend;

    public ClsParticipant( Integer   data_id_participant
                         , ClsPerson data_person
                         , Integer   data_friend
    ) {
        this.data_id_participant   = data_id_participant;
        this.data_person           = data_person;
        this.data_friend           = data_friend;
    }

    public Integer getData_id_participant() {
        return data_id_participant;
    }

    public void setData_id_participant(Integer data_id_team) {
        this.data_id_participant = data_id_team;
    }

    public Integer getData_friend() {
        return data_friend;
    }

    public void setData_friend(Integer data_friend) {
        this.data_friend = data_friend;
    }

    public ClsPerson getData_person() {
        return data_person;
    }

    public void setData_id_person(ClsPerson data_person) {
        this.data_person = data_person;
    }

    @Override
    public String toString() {
        return "ClsParticipant{" +
                "id_participant=" + data_id_participant +
                ", person=" + data_person +
                ", friend=" + data_friend +
                '}';
    }
}
