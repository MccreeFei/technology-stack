package cn.mccreefei.technologystack.leetcode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author MccreeFei
 * @create 2019-06-25 上午11:46
 * @refer <href>https://leetcode.com/problems/implement-queue-using-stacks/</href>
 * @idea push时使用辅助栈
 */
public class MyQueue {
    private Deque<Integer> stack;
    private Deque<Integer> helpStack;

    /** Initialize your data structure here. */
    public MyQueue() {
        stack = new LinkedList<>();
        helpStack = new LinkedList<>();
    }

    /** Push element x to the back of queue. */
    public void push(int x) {
       while (!stack.isEmpty()) {
           helpStack.push(stack.poll());
       }
       stack.push(x);
       while (!helpStack.isEmpty()) {
           stack.push(helpStack.poll());
       }
    }

    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
        return stack.pop();
    }

    /** Get the front element. */
    public int peek() {
        return stack.peek();
    }

    /** Returns whether the queue is empty. */
    public boolean empty() {
        return stack.isEmpty();
    }
}

/**
 * Your MyQueue object will be instantiated and called as such:
 * MyQueue obj = new MyQueue();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.empty();
 */