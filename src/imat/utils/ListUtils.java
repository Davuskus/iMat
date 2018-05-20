package imat.utils;

import java.util.ArrayList;
import java.util.List;

public final class ListUtils {

    public static <T> List<T> getReversedList(List<T> list) {
        List<T> reversedList = new ArrayList<>(list.size());
        for (int i = list.size() - 1; i >= 0; i--) {
            reversedList.add(list.get(i));
        }
        return reversedList;
    }

    public static <T> List<T> cloneList(List<T> list) {
        List<T> clonedList = new ArrayList<>(list.size());
        clonedList.addAll(list);
        return clonedList;
    }

}
