package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-09-18 下午1:46
 * @refer <href>https://leetcode.com/problems/robot-bounded-in-circle/</href>
 * @idea 在一次指令全部走完后 如果机器人在起点或者机器人的前进方向改变 则走的是一个循环
 */
public class RobotBoundedInCircle {
    public boolean isRobotBounded(String instructions) {
        int d = 0, x = 0, y = 0;
        int[][] move = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        for (int i = 0; i < instructions.length(); i++) {
            char c = instructions.charAt(i);
            if (c == 'R') {
                d = (d  + 1) % 4;
            } else if (c == 'L') {
                d = (d + 3) % 4;
            } else {
                x += move[d][0];
                y += move[d][1];
            }
        }
        return (x == 0 && y == 0) || d != 0;
    }
}
