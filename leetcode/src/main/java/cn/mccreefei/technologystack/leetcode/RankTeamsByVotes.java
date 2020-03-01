package cn.mccreefei.technologystack.leetcode;

import java.util.*;

/**
 * @author MccreeFei
 * @create 2020-03-01 上午10:20
 * @refer <href>https://leetcode.com/problems/rank-teams-by-votes/</href>
 * @idea map记录每个字符在各个位置的评分数量 排序 得出结果
 */
public class RankTeamsByVotes {
    public String rankTeams(String[] votes) {
       Map<Character, int[]> map = new HashMap<>();
       int len = votes[0].length();
       for (String vote : votes) {
           for (int i = 0; i < len; i++) {
               map.putIfAbsent(vote.charAt(i), new int[26]);
               map.get(vote.charAt(i))[i]++;
           }
       }

        List<Character> list = new LinkedList<>(map.keySet());
        Collections.sort(list, (a, b) -> {
            int[] aCount = map.get(a);
            int[] bCount = map.get(b);
            for (int i = 0; i < 26; i++) {
                if (aCount[i] != bCount[i]) {
                    return bCount[i] - aCount[i];
                }
            }
            return a - b;
        });
        StringBuffer sb = new StringBuffer();
        for (char c : list) {
            sb.append(c);
        }
        return sb.toString();
    }
}
