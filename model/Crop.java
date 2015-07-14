package model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by gitmaal on 12/06/2015.
 */

public class Crop {

    private Long id;
    private String name;
    private List<Crop> favorable;
    private CropType type;
    private CropSeason season;
    private Integer cycles;

    public Crop() {
    }

    public Crop(String name) {
        this.name = name;
        favorable = new LinkedList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Crop> getFavorable() {
        return favorable;
    }

    public void setFavorable(List<Crop> favorable) {
        this.favorable = favorable;
    }


    public void addFavorable(Crop crop) {
        this.favorable.add(crop);
    }

    public CropType getType() {
        return type;
    }

    public void setType(CropType type) {
        this.type = type;
    }

    public CropSeason getSeason() {
        return season;
    }

    public void setSeason(CropSeason season) {
        this.season = season;
    }

    public Integer getCycles() {
        return cycles;
    }

    public void setCycles(Integer cycles) {
        this.cycles = cycles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Crop)) return false;

        Crop crop = (Crop) o;

        return name.equals(crop.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Crop {name = ");
        sb.append(name).append(", type = ").append(type);
        if (favorable != null) {
            sb.append(", favorable = [");
            for (Crop c : favorable) {
                sb.append(c.name).append(",");
            }
            if (sb.charAt(sb.length() - 1) == ',')
                sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("]");

        return sb.toString();
    }
}
