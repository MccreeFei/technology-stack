package cn.mccreefei.technologystack.leetcode;

import jdk.internal.org.objectweb.asm.Handle;

/**
 * @author MccreeFei
 * @create 2019-12-16 上午9:40
 * @refer <href>https://leetcode.com/problems/happy-number/</href>
 * @idea 快慢指针
 */
public class HappyNumber {
    public boolean isHappy(int n) {
        int slow = sumSquaresOfDigits(n);
        int fast = sumSquaresOfDigits(slow);
        while (slow != fast) {
            slow = sumSquaresOfDigits(slow);
            fast = sumSquaresOfDigits(fast);
            fast = sumSquaresOfDigits(fast);
        }
        return slow == 1;
    }

    private int sumSquaresOfDigits(int n) {
        int sum = 0;
        int tmp = 0;
        while (n > 0) {
           tmp  = n % 10;
           sum += tmp * tmp;
           n = n / 10;
        }
        return sum;
    }
}
