
import model.Crop;
import model.CropCycle;
import model.CropInOrder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private int attempts;
    private final List<Crop> crops;
    private final int size;
    private final int cycles;
    private final Algorithm a;
    private final int maxAttempts;

    public Main(String[] args) throws IOException {

        crops = IOUtils.loadAll(args[0]);
        size = Integer.parseInt(args[1]);
        cycles = Integer.parseInt(args[2]);
        maxAttempts = size * 2;
        a = new Algorithm(size - 1, crops);
        System.out.print("\n\tREZULTATE\n");
        execute();
    }

    public void execute() {
        attempts++;
//        System.out.println("CULTURILE CITITE DIN FISIERELE DE INTRARE");
//        for (Crop c : crops) {
//            System.out.println(c);
//        }
        List<CropCycle> history = new ArrayList<>();
       // try {
            //primul ciclu de cultivare
            CropCycle cc1 = a.solve(null);
            history.add(cc1);

            for (int i = 0; i < 1000 && history.size() < cycles; i++) {
                cc1 = a.solve(history);
                history.add(cc1);
            }

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

//        } catch (NullPointerException e) {
//            System.out.print("\n\tImposibilitate gasire solutie, micsorati numarul de randuri de legume sau adaugati legume in index.\n");
//            System.out.print("\n\tAcum sunt " + crops.size() + " legume in index si se cer " + size + " randuri de legume");
//            System.exit(-1);
//        }
    }

    public static void main(String[] args) throws IOException {

        new Main(args);
    }
}
