package model;

import java.util.ArrayList;

public class clsMyFriend {
    private clsPerson data_person;
    private ArrayList<clsWish> data_wish;

    public clsMyFriend( clsPerson data_person
                      , ArrayList<clsWish> data_wish
    ) {
        this.data_person = data_person;
        this.data_wish   = data_wish;
    }

    public clsPerson getData_person() {
        return data_person;
    }

    public void setData_person(clsPerson data_person) {
        this.data_person = data_person;
    }

    public ArrayList<clsWish> getData_wish() {
        return data_wish;
    }

    public void setData_wish(ArrayList<clsWish> data_wish) {
        this.data_wish = data_wish;
    }
}
