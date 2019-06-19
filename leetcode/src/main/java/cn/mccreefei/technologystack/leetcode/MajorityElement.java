package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-06-19 上午10:58
 * @refer <href>https://leetcode.com/problems/majority-element/description/</href>
 * @idea 投票法 相同数count++ 否则count-- 最终留下数量最多的那个数
 */
public class MajorityElement {
    public int majorityElement(int[] nums) {
        int count = 1;
        int majority = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (count == 0) {
                majority = nums[i];
            }
            if (nums[i] == majority) {
                count++;
            }else {
                count--;
            }
        }
        return majority;
    }
}
