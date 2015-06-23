package model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Created by gitmaal on 12/06/2015.
 */
public class CropCycle {

    private Stack<CropInOrder> cropsInOrder;
    private int index;

    public CropCycle() {
        this.cropsInOrder = new Stack<>();
        this.index = 0;
    }

    public CropCycle(Stack<CropInOrder> cropsInOrder, int index) {
        this.cropsInOrder = new Stack<>();
        cropsInOrder.addAll(cropsInOrder);
        this.index = index;
    }

    public Stack<CropInOrder> getCropsInOrder() {
        return cropsInOrder;
    }

    public void setCropsInOrder(Stack<CropInOrder> cropsInOrder) {
        this.cropsInOrder = cropsInOrder;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void addCultura(CropInOrder cultura) {
        for (CropInOrder inLinie : cropsInOrder) {
            if (cultura.getPosition() == inLinie.getPosition()) {
                throw new IllegalStateException("Position already taken: " + cultura.getPosition());
            }
        }

        this.cropsInOrder.add(cultura);
    }

    public CropType typeForPosition(int position) {
        for (CropInOrder inLinie : cropsInOrder) {
            if (position == inLinie.getPosition()) {
                return inLinie.getCrop().getType();
            }
        }
        return null;
    }

    public List<Crop> favorablesForPosition(int position) {
        for (CropInOrder inLinie : cropsInOrder) {
            if (position == inLinie.getPosition()) {
                return inLinie.getCrop().getFavorable();
            }
        }
        return Collections.EMPTY_LIST;
    }

    public boolean contains(Crop crop) {
        for (CropInOrder inLinie : cropsInOrder) {
            if (crop.equals(inLinie.getCrop())) {
                return true;
            }
        }
        return false;
    }
}
