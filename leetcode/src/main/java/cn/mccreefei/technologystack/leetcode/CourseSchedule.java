package cn.mccreefei.technologystack.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author MccreeFei
 * @create 2019-10-23 上午10:48
 * @refer <href>https://leetcode.com/problems/course-schedule/</href>
 * @idea bfs
 */
public class CourseSchedule {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        if (prerequisites == null || prerequisites.length < 1) {
            return true;
        }
        /*
        courseDecide[i]:其他依赖第i个课程的课程列表
         */
        List[] courseDecide = new ArrayList[numCourses];
        /*
        degrees[i]:第i个课程依赖其他课程数
         */
        int[] degrees = new int[numCourses];
        int count = 0;
        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < numCourses; i++) {
            courseDecide[i] = new ArrayList<Integer>();
        }

        for (int i = 0; i < prerequisites.length; i++) {
            degrees[prerequisites[i][0]]++;
            courseDecide[prerequisites[i][1]].add(prerequisites[i][0]);
        }

        for (int i = 0; i < numCourses; i++) {
            if (degrees[i] == 0) {
                count ++;
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            Integer poll = queue.poll();
            for (int i = 0; i < courseDecide[poll].size(); i++) {
                int degree = (int) courseDecide[poll].get(i);
                degrees[degree]--;
                if (degrees[degree] == 0) {
                    count++;
                    queue.offer(degree);
                }
            }
        }
        return count >= numCourses;
    }
}
