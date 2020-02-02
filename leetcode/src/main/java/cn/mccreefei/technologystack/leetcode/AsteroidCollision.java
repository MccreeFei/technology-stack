package cn.mccreefei.technologystack.leetcode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author MccreeFei
 * @create 2020-02-02 下午2:24
 * @refer <href>https://leetcode.com/problems/asteroid-collision/</href>
 * @idea 栈操作
 */
public class AsteroidCollision {
    public int[] asteroidCollision(int[] asteroids) {
        LinkedList<Integer> stack = new LinkedList<>();
        LinkedList<Integer> result = new LinkedList<>();
        for (int i = 0; i < asteroids.length; i++) {
            if (asteroids[i] > 0) {
                stack.push(asteroids[i]);
            } else {
                while (!stack.isEmpty() && asteroids[i] != 0) {
                    Integer pop = stack.pop();
                    if (pop == (-asteroids[i])) {
                        asteroids[i] = 0;
                    } else if (pop > (-asteroids[i])) {
                        asteroids[i] = 0;
                        stack.push(pop);
                    }
                }
                if (stack.isEmpty() && asteroids[i] != 0) {
                    result.add(asteroids[i]);
                }
            }
        }
        int idx = 0;
        while (!stack.isEmpty()) {
            result.add(result.size() - idx++, stack.pop());
        }
        int[] res = new int[result.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = result.pollFirst();
        }
        return res;
    }

}
