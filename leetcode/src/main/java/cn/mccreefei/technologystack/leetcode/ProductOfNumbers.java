package cn.mccreefei.technologystack.leetcode;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.util.*;

/**
 * @author MccreeFei
 * @create 2020-02-16 上午11:00
 * @refer <href>https://leetcode.com/problems/product-of-the-last-k-numbers/</href>
 * @idea lst记录前n个数的积 遇0重新开始
 */
public  class ProductOfNumbers {
    private List<Integer> lst = new ArrayList<>();
    public ProductOfNumbers() {
        lst.add(1);
    }

    public void add(int num) {
        if (num == 0) {
            lst.clear();
            lst.add(1);
        } else {
            lst.add(num * lst.get(lst.size() - 1));
        }
    }

    public int getProduct(int k) {
        return k < lst.size() ? lst.get(lst.size() - 1) / lst.get(lst.size() - k - 1) : 0;
    }

}
