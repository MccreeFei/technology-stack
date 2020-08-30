package cn.mccreefei.technologystack.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MccreeFei
 * @create 2020-08-30 下午2:17
 * @refer <href>https://leetcode.com/problems/pancake-sorting/</href>
 * @idea 找寻最大数， 翻转最大数到数组头部， 再翻转到尾部
 */
public class PancakeSorting {
    public List<Integer> pancakeSort(int[] A) {
        List<Integer> res = new ArrayList<>();
        for (int x = A.length; x > 0; x--) {
            int i = 0;
            while (A[i] != x) {
                i++;
            }
            res.add(i + 1);
            reserve(A, i + 1);
            res.add(x);
            reserve(A, x);
        }
        return res;
    }

    private void reserve(int[] a, int k) {
        for (int i = 0, j = k - 1; i < j; i++, j--) {
            int tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
    }
}
