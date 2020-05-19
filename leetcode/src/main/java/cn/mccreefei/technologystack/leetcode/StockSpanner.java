package cn.mccreefei.technologystack.leetcode;

import javafx.util.Pair;

import java.util.LinkedList;

/**
 * @author MccreeFei
 * @create 2020-05-19 下午3:47
 * @refer <href>https://leetcode.com/problems/online-stock-span/</href>
 * @idea 栈
 */
public class StockSpanner {
    private LinkedList<Pair<Integer, Integer>> stack;
    public StockSpanner() {
        this.stack = new LinkedList<>();
    }

    public int next(int price) {
        int res = 1;
        while (!stack.isEmpty() && stack.peek().getKey() <= price) {
            res += stack.pop().getValue();
        }
        stack.push(new Pair<>(price, res));
        return res;
    }
}
