
import model.Crop;
import model.CropCycle;
import model.CropInOrder;

import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * Created by edi on 6/22/2015.
 */
public class Algorithm {

    private List<Crop> crops;
    private int size = 0;
    private List<CropCycle> history;
    private Stack<CropInOrder> current;

    public CropCycle solve(List<CropCycle> history) {
        this.history = history;
        current = new Stack<>();

        recursive();
        if (isSolution()) {
            CropCycle cc = new CropCycle();
            cc.setCropsInOrder(current);
            cc.setIndex(history == null ? 0 : history.size());
            return cc;
        }
        return null;
    }

    private boolean recursive() {
        if (!isSolution()) {
            CropInOrder previous = current.isEmpty() ? null : current.peek();
            if (previous == null) {
                if (history == null || history.isEmpty()) {
                    Random r = new Random(System.currentTimeMillis());
                    CropInOrder cin = new CropInOrder();
                    cin.setCrop(crops.get(r.nextInt(crops.size())));
                    cin.setPosition(0);
                    current.push(cin);
                } else {
                    CropCycle cc = history.get(history.size() - 1);
                    Crop prevCrop = cc.getCropsInOrder().get(0).getCrop();
                    for (Crop c : crops) {
                        if (prevCrop.getType().getNext().equals(c.getType())) {
                            CropInOrder cin = new CropInOrder();
                            cin.setCrop(c);
                            cin.setPosition(0);
                            current.push(cin);
                            break;
                        }
                    }
                }
                return recursive();
            } else {
                int newPosition = previous.getPosition() + 1;
                for (Crop currentCrop : previous.getCrop().getFavorable()) {

                    //constrangeri pentru urmatorul element
                    if (!isValidNext(currentCrop, newPosition)) {
                        continue;
                    }

                    CropInOrder cin = new CropInOrder();
                    cin.setCrop(currentCrop);
                    cin.setPosition(newPosition);
                    current.push(cin);

                    // go recursive
                    if (recursive()) {
                        return true;
                    }
                    //schimba optiunea
                    current.pop();
                    current.peek().setAfter(null);
                }
            }
            return false;
        }
        return true;
    }

    private boolean isValidNext(Crop crop, int newPosition) {

        if (history != null && !history.isEmpty()) {
            CropCycle cc = history.get(history.size() - 1);
            Crop prevCrop = cc.getCropsInOrder().get(newPosition).getCrop();
            if (prevCrop.getType().getNext().equals(crop.getType()) && prevCrop.getFavorable().contains(crop)) {
                //System.out.println("previous: " + prevCrop + ", next: " + prevCrop.getType().getNext()+ ", actual " + crop);
                return true;
            }
        } else {
            CropInOrder prev = current.peek();
            if (prev.getCrop().getType().getNext().equals(crop.getType())) {
                return true;
            }
        }
        return false;
    }

    private boolean isSolution() {
        return current.size() > size;
    }

    public void setCrops(List<Crop> crops) {
        this.crops = crops;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
