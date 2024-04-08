package model;

import java.util.Comparator;

public class PersonalComparator implements Comparator<Integer> {
    public int compare(Integer i, Integer k) {
        return Integer.compare(i, k);
    }
}
