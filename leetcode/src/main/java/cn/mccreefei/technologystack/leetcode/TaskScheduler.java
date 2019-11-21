package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-11-21 上午10:09
 * @refer <href>https://leetcode.com/problems/task-scheduler/</href>
 * @idea 贪心算法
 */
public class TaskScheduler {
    public int leastInterval(char[] tasks, int n) {
        int[] count = new int[26];
        //最大数的数量
        int max = 0;
        //达到最大数量的数多少个
        int maxCount = 0;
        for (char task : tasks) {
            int taskCount = ++count[task - 'A'];
            if (taskCount == max) {
               maxCount++;
           } else if (taskCount > max) {
               max = taskCount;
               maxCount = 1;
           }
        }
        //中间空的位置量
        int emptyCount = (max - 1) * n;
        //其他最大值占的中间位置量
        int slotCount = (max - 1) * (maxCount - 1);
        //剩余需要在中间插入的任务量
        int remainTaskCount = tasks.length - (maxCount) * max;
        //需要在中间插入idle的数量
        int idleCount = Math.max(0, emptyCount - slotCount - remainTaskCount);
        return idleCount + tasks.length;
    }
}
