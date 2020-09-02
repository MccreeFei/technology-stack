package cn.mccreefei.technologystack.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MccreeFei
 * @create 2020-09-02 下午5:25
 * @refer <href>https://leetcode.com/problems/largest-component-size-by-common-factor/</href>
 * @idea 并查集
 */
public class LargestComponentSizeByCommonFactor {
    class UnionFind {
        int[] p;
        int[] size;
        int max;
        UnionFind(int n) {
            p = new int[n];
            size = new int[n];
            max = 1;
            for (int i = 0; i < n; i++) {
                p[i] = i;
                size[i] = 1;
            }
        }

        int find(int x) {
            if (x != p[x]) {
                p[x] = find(p[x]);
            }
            return p[x];
        }

        void union(int x, int y) {
            int px = find(x);
            int py = find(y);
            if (px == py) {
                return;
            }
            if (size[px] < size[py]) {
                p[px] = py;
                size[py] += size[px];
                max = Math.max(max, size[py]);
            } else {
                p[py] = px;
                size[px] += size[py];
                max = Math.max(max, size[px]);
            }
        }

    }
    public int largestComponentSize(int[] A) {
        Map<Integer, Integer> map = new HashMap<>();
        UnionFind uf = new UnionFind(A.length);
        for (int a = 0; a < A.length; a++) {
            for (int i = 2; i * i <= A[a]; i++) {
                if (A[a] % i == 0) {
                    putOrUnion(uf, map, i, a);
                    putOrUnion(uf, map, A[a] / i, a);
                }
            }
            //自身因子不要忘记
            putOrUnion(uf, map, A[a], a);
        }
        return uf.max;
    }

    /**
     * key 因子不存在就合并 否则添加到map中 因子->值的index
     */
    private void putOrUnion(UnionFind uf, Map<Integer, Integer> map, int key, int val) {
        Integer v = map.get(key);
        if (v == null) {
            map.put(key, val);
        } else {
            uf.union(val, v);
        }
    }
}
