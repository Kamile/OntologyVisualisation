import concrete.ConcreteCOP;
import util.Permutation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PermutationTest {
    public static void main(String[] args) {
        List<ConcreteCOP> COPs = new ArrayList<>();
        COPs.add(new ConcreteCOP(1, new HashSet<>(), null, null));
        COPs.add(new ConcreteCOP(2, new HashSet<>(), null, null));
        COPs.add(new ConcreteCOP(3, new HashSet<>(), null, null));
        List<List<ConcreteCOP>> perms = Permutation.generatePermutations(COPs);

        for (List<ConcreteCOP> perm: perms) {
            for (ConcreteCOP cop: perm) {
                System.out.print(cop.getId());
            }
            System.out.println();
        }
    }
}
