package cn.mccreefei.technologystack.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * @author MccreeFei
 * @create 2020-01-28 下午3:10
 * @refer <href>https://leetcode.com/problems/array-nesting/</href>
 * @idea 记录访问过的index, 从被访问过的index开始最终结果一定不是最大, 每个圆环没有tail,从哪个index开始,循环就从该index开始
 */
public class ArrayNesting {
    public int arrayNesting(int[] nums) {
        boolean[] visited = new boolean[nums.length];
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            if (!visited[i]) {
               max = Math.max(max, dfs(nums, visited, i));
            }
        }
        return max;
    }

    private int dfs(int[] nums, boolean[] visited, int index) {
        int count = 0;
        int i = index;
        while (count == 0 || i != index) {
            visited[i] = true;
            i = nums[i];
            count++;
        }
        return count;
    }

}
