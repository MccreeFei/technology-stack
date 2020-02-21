package cn.mccreefei.technologystack.leetcode;

import java.util.*;

/**
 * @author MccreeFei
 * @create 2020-02-21 上午10:53
 * @refer <href>https://leetcode.com/problems/course-schedule-ii/</href>
 * @idea 参照注释
 */
public class CourseScheduleII {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        //每个课程依赖其他课程的数量
        int[] dependCount = new int[numCourses];
        //课程决定其他课程的Map
        Map<Integer, List<Integer>> decideMap = new HashMap();
        for (int[] prerequisite : prerequisites) {
            dependCount[prerequisite[0]] ++;
            List<Integer> lst = decideMap.computeIfAbsent(prerequisite[1], k -> new ArrayList<>());
            lst.add(prerequisite[0]);
        }
        int[] result = new int[numCourses];
        int index = 0;
        //依赖数为0的课程队列 此刻可学课程
        LinkedList<Integer> zeroCourseQueue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (dependCount[i] == 0) {
                zeroCourseQueue.offer(i);
            }
        }
        while (!zeroCourseQueue.isEmpty()) {
            Integer poll = zeroCourseQueue.poll();
            result[index++] = poll;
            List<Integer> lst = decideMap.get(poll);
            //将所有以该课程为先导的课程依赖数减一
            if (lst != null && lst.size() > 0) {
                for (int c : lst) {
                    if (--dependCount[c] == 0) {
                        zeroCourseQueue.offer(c);
                    }
                }
            }
        }
        return index == numCourses ? result : new int[]{};
    }


}
