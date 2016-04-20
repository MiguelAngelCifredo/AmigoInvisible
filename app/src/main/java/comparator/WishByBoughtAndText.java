package comparator;

import java.util.Comparator;

import model.ClsWish;

public class WishByBoughtAndText implements Comparator<ClsWish> {
    public int compare(ClsWish object1, ClsWish object2) {
        int res;
        res = object2.getData_bought().compareTo(object1.getData_bought());
        if (res !=0 ) { return res; }
        res = object1.getData_text().compareTo(object2.getData_text());
        return res;
    }
}