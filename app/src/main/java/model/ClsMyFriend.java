package model;

import java.util.ArrayList;

public class ClsMyFriend {

    private ClsPerson data_person;
    private ArrayList<ClsWish> data_wish;
    private String data_file_path;

    public ClsMyFriend( ClsPerson data_person
                      , ArrayList<ClsWish> data_wish
    ) {
        this.data_person = data_person;
        this.data_wish   = data_wish;
    }

    public ClsPerson getData_person() {
        return data_person;
    }

    public void setData_person(ClsPerson data_person) {
        this.data_person = data_person;
    }

    public ArrayList<ClsWish> getData_wish() {
        return data_wish;
    }

    public void setData_wish(ArrayList<ClsWish> data_wish) {
        this.data_wish = data_wish;
    }

    public String getData_file_path() {
        return data_file_path;
    }

    public void setData_file_path(String data_file_path) {
        this.data_file_path = data_file_path;
    }

    @Override
    public String toString() {
        return "ClsMyFriend{" +
                "person=" + data_person +
                ", wish=" + data_wish +
                '}';
    }
}