package cn.mccreefei.technologystack.leetcode;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MccreeFei
 * @create 2019-11-06 下午1:56
 * @refer <href>https://leetcode.com/problems/queue-reconstruction-by-height/</href>
 * @idea 每次挑选出高度最高的人,以k的顺序添加到链表(后面都是比他矮的人不会影响k),循环加入所有人,此时为最终顺序转化数组即可
 */
public class QueueReconstructionByHeight {
    public int[][] reconstructQueue(int[][] people) {
        Arrays.sort(people, (o1, o2) -> o1[0] == o2[0] ? o1[1] - o2[1] : o2[0] - o1[0]);
        List<int[]> lst = new LinkedList<>();
        for (int[] person : people) {
            lst.add(person[1], person);
        }
        return lst.toArray(people);
    }
}
