package cn.mccreefei.technologystack.leetcode;


import java.util.*;

/**
 * @author MccreeFei
 * @create 2020-06-28 下午5:20
 * @refer <href>https://leetcode.com/problems/reconstruct-itinerary/</href>
 * @idea 始终有条路径是valid的，所以当dfs到死路时,代表这个死路是最终的出路,dfs逆序添加到最终的结果列表即可
 */
public class ReconstructItinerary {
    public List<String> findItinerary(List<List<String>> tickets) {
        Map<String, PriorityQueue<String>> map = new HashMap<>(tickets.size());
        for (List<String> ticket : tickets) {
            map.computeIfAbsent(ticket.get(0), k -> new PriorityQueue<String>()).offer(ticket.get(1));
        }
        String source = "JFK";
        List<String> result = new LinkedList<>();
        dfs(map, source, result);
        return result;
    }

    private void dfs(Map<String, PriorityQueue<String>> map, String source, List<String> res) {
        PriorityQueue<String> queue = map.get(source);
        while (queue != null && !queue.isEmpty()) {
            dfs(map, queue.poll(), res);
        }
        res.add(0, source);
    }
}
