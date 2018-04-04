package util;

import concrete.ConcreteCOP;

import java.util.ArrayList;
import java.util.List;

/**
 * Heap's algorithm for generating all permutations of a list of n objects.
 */
public class Permutation {
    private static List<ConcreteCOP> mList;
    private static List<List<ConcreteCOP>> mPermutations;

    public static List<List<ConcreteCOP>> generatePermutations(List list) {
        mList = list;
        mPermutations = new ArrayList<>();
        helper(list.size(), mList);
        return mPermutations;
    }

    private static void helper(int n, List<ConcreteCOP> list) {
        if (n <= 1) {
            mPermutations.add(new ArrayList<>(list));
        } else {
            for (int i = 0; i < n - 1; i++) {
                helper(n-1, list);

                if (n % 2 == 0) {
                    swap(i, n-1);
                } else {
                    swap(0, n-1);
                }
            }
            helper(n-1, list);
        }
    }

    private static void swap(int i1, int i2) {
        ConcreteCOP tmp = mList.get(i1);
        mList.set(i1, mList.get(i2));
        mList.set(i2, tmp);
    }
}
