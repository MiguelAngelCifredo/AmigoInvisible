package comparator;

import java.util.Comparator;

import model.ClsPerson;

public class PersonName implements Comparator<ClsPerson> {
    public int compare(ClsPerson object1, ClsPerson object2) {
        return object1.getData_name().toUpperCase().compareTo(object2.getData_name().toUpperCase());
    }
}