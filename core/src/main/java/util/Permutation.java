package util;

import java.util.ArrayList;
import java.util.List;

/**
 * Heap's algorithm for generating all permutations of a list of n objects.
 * @param <T>
 */
public class Permutation<T> {
    private List<T> list2;
    private List<List<T>> permutations2;

    public List<List<T>> permutations2(List<T> list) {
        this.list2 = list;
        permutations2 = new ArrayList<>();
        helper2(list.size(), list2);
        return permutations2;
    }

    private void helper2(int n, List<T> list) {
        if (n == 1) {
            permutations2.add(list);
        } else {
            for (int i = 0; i < n - 1; i++) {
                helper2(n-1, list);

                if (n % 2 == 0) {
                    swap2(i, n-1);
                } else {
                    swap2(0, n-1);
                }
            }
            helper2(n-1, list);
        }
    }

    private void swap2(int i1, int i2) {
        T tmp = list2.get(i1);
        list2.set(i2, list2.get(i1));
        list2.set(i1, tmp);
    }
}
