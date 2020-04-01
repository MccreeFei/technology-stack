package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-04-01 下午4:32
 * @refer <href>https://leetcode.com/problems/sqrtx/</href>
 * @idea 二分法
 */
public class MySqrt {
    public int mySqrt(int x) {
        int start = 1, end = x;
        while (start <= end) {
            int mid = start + (end - start)/2;
            if (mid == x/mid) {
                return mid;
            } else if (mid < x/mid) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return start - 1;
    }

}
