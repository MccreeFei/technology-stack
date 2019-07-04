package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-07-04 上午11:24
 * @refer <href>https://leetcode.com/problems/container-with-most-water/</href>
 * @idea 双指针  最大面积一定指向短柱子的指针向内移动然后比较面积最大值 因为长度减少 高度再减少面积一定不会最大
 */
public class ContainerWithMostWater {
    public int maxArea(int[] height) {
        int left = 0;
        int right = height.length-1;
        int maxSize = 0;
        while (left < right) {
            maxSize = Math.max(maxSize, Math.min(height[left], height[right]) * (right-left));
            if (height[left] < height[right]) {
                left++;
            }else {
                right--;
            }
        }
        return maxSize;
    }
}
