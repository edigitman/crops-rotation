package model;

/**
 * Created by edi on 6/15/2015.
 */
public enum CropType {
    BIG(1), MEDIUM(2), SHORT(3), INGRASAMANT(0);

    private int next;

    private CropType(int c){
        this.next = c;
    }

    public CropType getNext(){
        return CropType.values()[next];
    }

}
