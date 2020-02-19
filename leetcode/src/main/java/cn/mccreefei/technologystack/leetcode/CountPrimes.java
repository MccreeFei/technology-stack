package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-02-19 上午11:42
 * @refer <href>https://leetcode.com/problems/count-primes/</href>
 * @idea 记录n个数是否是prime的状态,i从2开始遍历到sqrt(n)(因为2*3与3*2效果一样),j从2遍历到i*j小于n
 * prime[i*j]置为true 最后数素数的个数
 */
public class CountPrimes {
    public int countPrimes(int n) {
        boolean[] notPrime = new boolean[n];
        for (int i = 2; i * i < n; i++) {
            if (notPrime[i]) {
                continue;
            }
            for (int j = 2; i * j < n; j++) {
                notPrime[i * j] = true;
            }
        }
        int count = 0;
        for (int i = 2; i < n; i++) {
            if (!notPrime[i]) {
                count++;
            }
        }
        return count;
    }
}
