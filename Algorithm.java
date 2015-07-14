
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
    private Crop firstCrop = null;

    public CropCycle solve(List<CropCycle> history, Crop firstCrop) {
        this.history = history;
        current = new Stack<>();
        this.firstCrop = firstCrop;

        recursive();
        if (isSolution()) {
            CropCycle cc = new CropCycle();
            cc.setCropsInOrder(current);
            cc.setIndex(history == null ? 0 : history.size());
            return cc;
        }
        return null;
    }


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
                    CropInOrder cin = new CropInOrder();
                    cin.setPosition(0);

                    if (firstCrop == null) {
                        Random r = new Random(System.currentTimeMillis());
                        while (cin.getCrop() == null) {
                            cin.setCrop(crops.get(r.nextInt(crops.size())));
                            if (!CropType.BIG.equals(cin.getCrop().getType())) {
                                cin.setCrop(null);
                            }
                        }
                    } else {
                        cin.setCrop(firstCrop);
                    }


                    current.push(cin);
                } else {
                    CropCycle cc = history.get(history.size() - 1);
                    CropInOrder prevCrop = cc.getCropsInOrder().get(0);
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
                    cin.setBefore(previous);

                    CropInOrder prev = cin.getBefore().getBefore();

                    if (prev != null && !CropType.INGRASAMANT.equals(currentCrop.getType()) && prev.getCrop().getType().equals(currentCrop.getType())) {
                        continue;
                    }

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
            CropInOrder prevCrop = cc.getCropsInOrder().get(newPosition);
            if (checkSuccessionTypeUniqeness(prevCrop, crop))
                return true;
        } else {
            if (checkSuccessionTypeUniqeness(current.peek(), crop))
                return true;
        }
        return false;
    }

    private boolean checkSuccessionTypeUniqeness(CropInOrder previous, Crop current) {
        Crop crop = previous.getCrop();
        if (!crop.getType().getNext().contains(current.getType()) ||
                !crop.getFavorable().contains(current)) {
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
