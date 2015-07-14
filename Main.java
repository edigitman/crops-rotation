
import model.Crop;
import model.CropCycle;
import model.CropInOrder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class Main {

    private final List<Crop> crops;
    private final int size;
    private final int cycles;
    private final Algorithm a;
    private Properties prop = new Properties();

    public Main() throws IOException {

        InputStream input = null;
        try {
            input = new FileInputStream("config.txt");
            // load a properties file
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        crops = IOUtils.loadAll(prop.getProperty("fisier"));

        fixFavorableSimetry();
        List<Crop> errorCrops = validate();
        if (!errorCrops.isEmpty()) {
            System.out.print("\nPLANTE NEDEFINITE:\n");
            System.out.print("\nAceste plante trebuie sa aibe tip si cel putin o planta de asociere:\n");
            for (Crop crop : errorCrops) {
                System.out.println(crop);
            }

            //remove error crops
            for (Crop c : crops) {
                c.getFavorable().removeAll(errorCrops);
            }
            crops.removeAll(errorCrops);
        }

        size = Integer.parseInt(prop.getProperty("randuri"));
        cycles = Integer.parseInt(prop.getProperty("ani"));
        a = new Algorithm(size - 1, crops);

        printBefore();

        List<CropCycle> history = execute();

        print(history);
    }

    public List<CropCycle> execute() {

        List<CropCycle> history = new ArrayList<>();
        // try {
        //primul ciclu de cultivare
        int init = Integer.parseInt(prop.getProperty("initializat", "0"));
        switch (init) {
            case 1:
                history.add(a.solve(null, loadByName(prop.getProperty("initializat.1.prima.planta", ""))));
                break;
            case 2:
                CropCycle cropCycle = loadFirstLine(prop.getProperty("initializat.2.primul.rand", ""));
                if (cropCycle != null) {
                    history.add(cropCycle);
                } else {
                    history.add(a.solve(null));
                }
                break;
            default:
                history.add(a.solve(null));
        }


        for (int i = 0; i < 1000 && history.size() < cycles; i++) {
            history.add(a.solve(history));
        }

        return history;

//        } catch (NullPointerException e) {
//            System.out.print("\n\tImposibilitate gasire solutie, micsorati numarul de randuri de legume sau adaugati legume in index.\n");
//            System.out.print("\n\tAcum sunt " + crops.size() + " legume in index si se cer " + size + " randuri de legume");
//            System.exit(-1);
//        }
    }

    private void printBefore() {
        String print = prop.getProperty("afiseaza.plante", "0");

        if ("1".equals(print)) {
            System.out.println("\n\tCULTURILE FOLOSITE PENTRU A GENERA SOLUTII\n");
            for (Crop c : crops) {
                System.out.println(c);
            }
        }
    }


    private void print(List<CropCycle> history) {
        System.out.print("\n\tREZULTATE\n");
        for (CropCycle cc : history) {
            System.out.println("\n---- An cultivare: " + (cc.getIndex() + 1));
            double distance = 0;
            boolean space = true;
            for (CropInOrder cio : cc.getCropsInOrder()) {
                System.out.println(distance + (space ? " " : "")
                        + ": " + cio.getCrop().getName() + ", tip: " + cio.getCrop().getType());
                distance += 0.25;
                space = !space;
            }
        }
    }


    /**
     * Validates that all the crops have type and favorable list
     *
     * @return list of crops that don;t have type
     */
    private List<Crop> validate() {
        List<Crop> result = new ArrayList<>();
        for (Crop crop : crops) {
            if (crop.getType() == null || crop.getFavorable().isEmpty()) {
                result.add(crop);
            } else {
                for (Crop c : crop.getFavorable()) {
                    if (!c.getFavorable().contains(crop)) {
                        result.add(crop);
                        break;
                    }
                }
            }
        }
        return result;
    }

    /**
     * Adds simetry in the crops list
     */
    private void fixFavorableSimetry() {
        for (Crop crop : crops) {
            for (Crop c : crop.getFavorable()) {
                if (!c.getFavorable().contains(crop)) {
                    c.addFavorable(crop);
                }
            }
        }
    }

    private CropCycle loadFirstLine(String property) {
        if (property == null || property.isEmpty()) {
            return null;
        }
        CropCycle result = new CropCycle();
        String[] cropsNames = property.split(",");

        int position = 0;
        CropInOrder previous = null;
        for (String name : cropsNames) {
            Crop crop = loadByName(name);
            CropInOrder cin = new CropInOrder();
            cin.setCrop(crop);
            cin.setPosition(position);
            cin.setBefore(previous);
            result.addCultura(cin);

            previous = cin;
            position++;
        }
        return result;
    }

    private Crop loadByName(String name) {
        if (name != null && !name.isEmpty()) {
            for (Crop crop : crops) {
                if (crop.getName().equals(name)) {
                    return crop;
                }
            }
        }
        Random r = new Random();
        return crops.get(r.nextInt(crops.size()));
    }

    public static void main(String[] args) throws IOException {
        new Main();
    }
}
