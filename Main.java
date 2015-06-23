
import model.Crop;
import model.CropCycle;
import model.CropInOrder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private Algorithm a;
    private List<Crop> crops;
    private int size = 0;
    private int cycles = 0;

    public void execute(String[] args) throws IOException {
        crops = IOUtils.loadAll(args[0]);
        size = Integer.parseInt(args[1]);
        cycles = Integer.parseInt(args[2]);
        
        a = new Algorithm();
        a.setCrops(crops);
        a.setSize(size);

        System.out.println("CULTURILE CITITE DIN FISIERELE DE INTRARE");
        for (Crop c : crops) {
            System.out.println(c);
        }

        System.out.print("\n\tRESULTS\n");

        List<CropCycle> history = new ArrayList<>();

        //primul ciclu de cultivare
        CropCycle cc1 = a.solve(null);
        history.add(cc1);

        for (int i = 0; i < 100 && history.size() < cycles; i++) {
            cc1 = a.solve(history);
            history.add(cc1);
        }

        for (CropCycle cc : history) {
            System.out.println("\n---- cycle: " + (cc.getIndex() + 1));
            for (CropInOrder cio : cc.getCropsInOrder()) {
                System.out.print(cio.getCrop().getName() + "[" + cio.getCrop().getType() + "]");
            }
        }

        System.out.println();
    }

    public static void main(String[] args) throws IOException {

        new Main().execute(args);
    }
}
