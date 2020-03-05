package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-03-05 上午9:30
 * @refer <href>https://leetcode.com/problems/gas-station/</href>
 * @idea 只要total>=0 代表整个过程的加油gas>=消耗gas 一定会有一个起点可以跑完整个圆
 * 当sum<0时表示从之前start到当前station消耗大于加的gas，所以开始位置重置为当前位置下一个
 */
public class GasStation {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int n = gas.length;
        /**
         * 从当前gas[start]开始,剩余gas总和
         */
        int sum = 0;
        /**
         * 所有gas剩余
         */
        int total = 0;
        /**
         * gas station开始位置
         */
        int start = 0;
        for (int i = 0; i < n; i++) {
           sum += gas[i] - cost[i];
           if (sum < 0) {
               total += sum;
               sum = 0;
               start = i + 1;
           }
        }
        total += sum;
        return total < 0 ? -1 : start;
    }
}
