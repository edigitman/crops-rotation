package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edi on 6/15/2015.
 */
public enum CropType {
    BIG(2), MEDIUM(2), INGRASAMANT(0, 1);

    private int[] next;

    private CropType(int... c) {
        this.next = c;
    }

    public List<CropType> getNext() {
        List<CropType> result = new ArrayList<>();
        for (int type : next) {
            result.add(CropType.values()[type]);
        }
        return result;
    }

}
