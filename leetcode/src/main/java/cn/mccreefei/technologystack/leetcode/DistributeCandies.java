package cn.mccreefei.technologystack.leetcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author MccreeFei
 * @create 2019-06-27 上午11:00
 * @refer <href>https://leetcode.com/problems/distribute-candies/</href>
 * @idea 比较种类数 与 被分得糖果数
 */
public class DistributeCandies {
    public int distributeCandies(int[] candies) {
        Set<Integer> set = new HashSet<>();
        for (int num : candies) {
            set.add(num);
        }
        int num = candies.length / 2;
        return set.size() > num ? num : set.size();
    }
}
