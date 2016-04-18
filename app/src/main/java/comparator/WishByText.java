package comparator;

import java.util.Comparator;

import model.ClsWish;

public class WishByText implements Comparator<ClsWish> {
    public int compare(ClsWish object1, ClsWish object2) {
        return object1.getData_text().toUpperCase().compareTo(object2.getData_text().toUpperCase());
    }
}