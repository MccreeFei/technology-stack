package cn.mccreefei.technologystack.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author MccreeFei
 * @create 2020-03-09 上午11:17
 * @refer <href>https://leetcode.com/problems/insert-delete-getrandom-o1/</href>
 * @idea ArrayList存放值, map记录存放值->数组index, 重点在remove操作, 找到val所在index,
 * 将数组最后一个位置元素与该index对调，删除最后一个，同时记录之前最后一个元素的新位置。
 */
public class RandomizedSet {
    private ArrayList<Integer> lst;
    /**
     * val -> index
     */
    private Map<Integer, Integer> map;
    private Random random;
    /** Initialize your data structure here. */
    public RandomizedSet() {
        this.lst = new ArrayList<>();
        this.map = new HashMap<>();
        this.random = new Random();
    }

    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        if (map.containsKey(val)) {
            return false;
        }
        map.put(val, lst.size());
        lst.add(val);
        return true;
    }

    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {
        if (!map.containsKey(val)) {
            return false;
        }
        Integer index = map.get(val);
        lst.set(index, lst.get(lst.size() - 1));
        map.put(lst.get(index), index);
        lst.remove(lst.size() - 1);
        map.remove(val);
        return true;
    }

    /** Get a random element from the set. */
    public int getRandom() {
        return lst.get(random.nextInt(lst.size()));
    }

}
