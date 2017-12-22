package concrete;

import icircles.recomposition.RecompData;

import java.util.ArrayList;

public class BuildStep {

    public ArrayList<RecompData> recomp_data = new ArrayList();
    public BuildStep next = null;

    BuildStep(RecompData rd) {
        this.recomp_data.add(rd);
    }
}
