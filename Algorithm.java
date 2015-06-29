
import model.Crop;
import model.CropCycle;
import model.CropInOrder;
import model.CropType;

import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * Created by edi on 6/22/2015.
 */
public class Algorithm {

    public Algorithm(int size, List<Crop> crops) {
        this.size = size;
        this.crops = crops;
    }

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
                    Crop choose = null;
                    while (choose == null) {
                        choose = crops.get(r.nextInt(crops.size()));
                        if (!CropType.BIG.equals(choose.getType())) {
                            choose = null;
                        }
                    }

                    CropInOrder cin = new CropInOrder();
                    cin.setCrop(choose);
                    cin.setPosition(0);
                    current.push(cin);
                } else {
                    CropCycle cc = history.get(history.size() - 1);
                    Crop prevCrop = cc.getCropsInOrder().get(0).getCrop();
                    for (Crop crop : crops) {
                        if (checkSuccessionTypeUniqeness(prevCrop, crop)) {
                            CropInOrder cin = new CropInOrder();
                            cin.setCrop(crop);
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
            if (checkSuccessionTypeUniqeness(prevCrop, crop))
                return true;
        } else {
            if (checkSuccessionTypeUniqeness(current.peek().getCrop(), crop))
                return true;
        }
        return false;
    }

    private boolean checkSuccessionTypeUniqeness(Crop previous, Crop current) {
        if (!previous.getType().getNext().contains(current.getType()) ||
                !previous.getFavorable().contains(current)) {
            return false;
        }
        for (CropInOrder cio : this.current) {
            if (cio.getCrop().equals(current)) {
                return false;
            }
        }
        return true;
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
