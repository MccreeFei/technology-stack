package cn.mccreefei.technologystack.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @author MccreeFei
 * @create 2019-06-17 下午5:23
 * @refer <href>https://leetcode.com/problems/min-stack/description/</href>
 * @idea 使用两个栈 一个正常栈 一个只保留最小数值的栈
 */
public class MinStack {
    /** initialize your data structure here. */
    private int min;
    private Deque<Integer> stack;
    private Deque<Integer> minStack;
    public MinStack() {
        stack = new LinkedList<>();
        minStack = new LinkedList<>();
    }

    public void push(int x) {
        stack.push(x);
        if (minStack.isEmpty() || x <= minStack.peek()) {
            minStack.push(x);
        }
    }

    public void pop() {
        Integer pop = stack.pop();
        if (!minStack.isEmpty() && minStack.peek().equals(pop)) {
            minStack.pop();
        }
    }

    public int top() {
        if (stack.isEmpty()) {
            throw  new ArrayIndexOutOfBoundsException();
        }
        return stack.peek();
    }

    public int getMin() {
        if (minStack.isEmpty()) {
            throw  new ArrayIndexOutOfBoundsException();
        }
        return minStack.peek();
    }

}
