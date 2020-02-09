package cn.mccreefei.technologystack.leetcode;

import java.util.*;

/**
 * @author MccreeFei
 * @create 2020-02-09 上午11:20
 * @refer <href>https://leetcode.com/problems/tweet-counts-per-frequency/</href>
 * @idea Map + TreeSet.subSet()用法
 */
public class TweetCounts {
    private Map<String, TreeSet<Integer>> tweetMap;
    public TweetCounts() {
        tweetMap = new HashMap<>();
    }

    public void recordTweet(String tweetName, int time) {
        TreeSet<Integer> set = tweetMap.computeIfAbsent(tweetName, k -> new TreeSet<>());
        set.add(time);
    }

    public List<Integer> getTweetCountsPerFrequency(String freq, String tweetName, int startTime, int endTime) {
        int interval = "minute".equals(freq) ? 60 : "hour".equals(freq) ? 3600 : 24 * 3600;
        return help(tweetName, interval, startTime, endTime);
    }

    private List<Integer> help(String tweetName, int interval, int startTime, int endTime) {
        List<Integer> result = new LinkedList<>();
        if (!tweetMap.containsKey(tweetName)) {
            for (int i = startTime; i <= endTime; i += interval) {
                result.add(0);
            }
        } else {
            TreeSet<Integer> set = tweetMap.get(tweetName);
            for (int i = startTime; i <= endTime; i += interval) {
                result.add(set.subSet(i, Math.min(i + interval, endTime + 1)).size());
            }
        }
        return result;
    }
}
