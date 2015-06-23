package model;

/**
 * Created by edi on 6/15/2015.
 */
public class CropInOrder {

    private Crop crop;
    private int position;
    private CropInOrder before;
    private CropInOrder after;

    public Crop getCrop() {
        return crop;
    }

    public void setCrop(Crop crop) {
        this.crop = crop;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public CropInOrder getBefore() {
        return before;
    }

    public void setBefore(CropInOrder before) {
        this.before = before;
    }

    public CropInOrder getAfter() {
        return after;
    }

    public void setAfter(CropInOrder after) {
        this.after = after;
    }
}
